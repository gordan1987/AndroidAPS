package info.nightscout.androidaps.plugins.pump.danaRS.comm

import com.cozmo.danar.util.BleCommandUtil
import info.TestBase
import info.nightscout.androidaps.interfaces.ActivePluginProvider
import info.nightscout.androidaps.interfaces.Constraint
import info.nightscout.androidaps.logging.AAPSLogger
import info.nightscout.androidaps.plugins.bus.RxBusWrapper
import info.nightscout.androidaps.plugins.configBuilder.ConstraintChecker
import info.nightscout.androidaps.plugins.pump.danaR.DanaRPump
import info.nightscout.androidaps.plugins.pump.danaRS.DanaRSPlugin
import info.nightscout.androidaps.utils.resources.ResourceHelper
import info.nightscout.androidaps.utils.sharedPreferences.SP
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
@PrepareForTest(ConstraintChecker::class)
class DanaRSMessageHashTableTest : TestBase() {

    @Mock lateinit var aapsLogger: AAPSLogger
    @Mock lateinit var rxBus: RxBusWrapper
    @Mock lateinit var resourceHelper: ResourceHelper
    @Mock lateinit var danaRSPlugin: DanaRSPlugin
    @Mock lateinit var activePlugin: ActivePluginProvider
    @Mock lateinit var constraintChecker: ConstraintChecker
    @Mock lateinit var sp: SP
    lateinit var danaRPump: DanaRPump

    @Test
    fun runTest() {
        `when`(constraintChecker.applyBolusConstraints(anyObject())).thenReturn(Constraint(0.0))

        val danaRSMessageHashTable = DanaRSMessageHashTable(aapsLogger, rxBus, resourceHelper, danaRPump, danaRSPlugin, activePlugin, constraintChecker)
        val forTesting: DanaRS_Packet = DanaRS_Packet_APS_Set_Event_History(aapsLogger, DanaRPump.CARBS, 0, 0, 0)
        val testPacket: DanaRS_Packet = danaRSMessageHashTable.findMessage(forTesting.command)
        Assert.assertEquals(BleCommandUtil.DANAR_PACKET__OPCODE__APS_SET_EVENT_HISTORY.toLong(), testPacket.getOpCode().toLong())
    }

    @Before
    fun setup() {
        danaRPump = DanaRPump(aapsLogger, sp)
    }
}