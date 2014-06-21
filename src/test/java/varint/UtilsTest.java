package varint;

import com.hongweiyi.utils.varint.SimpleVarintUtils;
import com.hongweiyi.utils.varint.StreamVaruintUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author hongweiyi
 * @since 2014-Jun-21
 */
public class UtilsTest {

    @Test
    public void testSimple() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            int value = SimpleVarintUtils.varint2int(SimpleVarintUtils.int2varint(i));
            assertEquals(value, i);
        }
    }

    @Test
    public void testStream() {
        for (int i = 0; i < (Integer.MAX_VALUE >> 2); i++) {
            int value = StreamVaruintUtils.varint2int(StreamVaruintUtils.int2varint(i));
            assertEquals(value, i);
        }
    }
}
