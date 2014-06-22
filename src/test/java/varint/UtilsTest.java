package varint;

import com.hongweiyi.utils.varint.LengthedVaruintUtils;
import com.hongweiyi.utils.varint.NormalVarintUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author hongweiyi
 * @since 2014-Jun-21
 */
public class UtilsTest {

    @Test
    public void testNormal() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            int value = NormalVarintUtils.varint2int(NormalVarintUtils.int2varint(i));
            assertEquals(value, i);
        }
    }

    @Test
    public void testLengthed() {
        for (int i = 0; i < (Integer.MAX_VALUE >> 2); i++) {
            int value = LengthedVaruintUtils.varint2int(LengthedVaruintUtils.int2varint(i));
            assertEquals(value, i);
        }
    }

    @Test
    public void testGetLength() {

        for (int i = 0; i < (Integer.MAX_VALUE >> 2); i++) {
            byte[] bytes = LengthedVaruintUtils.int2varint(i);
            int len = bytes.length;
            assertEquals(len, LengthedVaruintUtils.getBytesLength(bytes[0]));
        }
    }
}
