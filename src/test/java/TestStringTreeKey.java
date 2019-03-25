import com.globallogic.test.tree.manager.StringTreeKey;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestStringTreeKey {
    @Test
    public void testGetMaxChildCount() {
        StringTreeKey stringTreeKey1 = new StringTreeKey("q");
        assertEquals(stringTreeKey1.getMaxChildCount(), 2);
        StringTreeKey stringTreeKey5 = new StringTreeKey("qwert");
        assertEquals(stringTreeKey5.getMaxChildCount(), 5);
        StringTreeKey stringTreeKey6 = new StringTreeKey("qwerty");
        assertEquals(stringTreeKey6.getMaxChildCount(), 10);
    }
}
