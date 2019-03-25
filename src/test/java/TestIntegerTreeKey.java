import com.globallogic.test.tree.manager.IntegerTreeKey;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestIntegerTreeKey {
    @Test
    public void testGetMaxChildCount() {
        IntegerTreeKey integerTreeKey1 = new IntegerTreeKey(1);
        assertEquals(integerTreeKey1.getMaxChildCount(), 2);
        IntegerTreeKey integerTreeKey5 = new IntegerTreeKey(5);
        assertEquals(integerTreeKey5.getMaxChildCount(), 5);
        IntegerTreeKey integerTreeKey6 = new IntegerTreeKey(6);
        assertEquals(integerTreeKey6.getMaxChildCount(), 10);
    }
}
