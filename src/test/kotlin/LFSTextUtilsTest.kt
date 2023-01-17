import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import lfs.ktinsim.utils.LFSTextUtils.toLFSBytes
import lfs.ktinsim.utils.LFSTextUtils.toUTF8String
import kotlin.test.assertEquals

class LFSTextUtilsTest {
    @Test
    fun CJK() {
        val koreanString = "갂"
        var expectedOutput = byteArrayOf('^'.code.toByte(), 'K'.code.toByte(), 0x81.toByte(), 0x41.toByte())
        assertContentEquals(expectedOutput, koreanString.toLFSBytes())

        val japaneseString = "阿"
        expectedOutput = byteArrayOf('^'.code.toByte(), 'J'.code.toByte(), 0x88.toByte(), 0xA2.toByte())
        assertContentEquals(expectedOutput, japaneseString.toLFSBytes())
    }

    @Test
    fun replaceLFSSpecialSymbols() {
        val s = "^v^^a^a^c^d^s^q^t^l^r^h".toByteArray()
        val expectedResult = "|^a*:\\/?\"<>#"
        val result = s.toUTF8String(0, s.size)
        assertEquals(expectedResult, result)
    }
}