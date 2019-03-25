import com.globallogic.test.tree.ITreeContainer;
import com.globallogic.test.tree.exception.EmptyKeyException;
import com.globallogic.test.tree.manager.IntegerTreeKey;
import com.globallogic.test.tree.observer.AbstractTreeManager;
import com.globallogic.test.tree.treecontainer.TreeContainer;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;

public class TestTreeContainer {

    @Test
    public void testSize() {
        ITreeContainer<AbstractTreeManager, String> st = new TreeContainer<>();
        AbstractTreeManager atm1 = new IntegerTreeKey(1);
        AbstractTreeManager atm2 = new IntegerTreeKey(2);
        AbstractTreeManager atm3 = new IntegerTreeKey(3);
        st.put(null, atm2 , "two");
        st.put(atm2, atm1, "eight");
        st.put(atm2, atm3, "twelve");
        st.put(atm3, atm1, "Message");
        st.put(atm3, atm1, "HELLO");
        System.out.println(st.getByKey(atm1));
    }

    @Test
    public void testGetAndPut() {
        ITreeContainer<AbstractTreeManager, String> st = new TreeContainer<>();
        try {
            st.put(null, null,"");
        } catch (EmptyKeyException e) {
            assertNotNull(e);
        }
        AbstractTreeManager atm1 = new IntegerTreeKey(1);
        st.put(null, atm1, "Test String1");
        assertEquals(st.getByKey(atm1).get(0), "Test String1");
        assertEquals(st.getByKey(atm1).size(), 1);
    }

    @Test
    public void testRemove() {
        ITreeContainer<AbstractTreeManager, String> st = new TreeContainer<>();
        AbstractTreeManager atm1 = new IntegerTreeKey(1);
        st.put(null, atm1, "Test String1");
        st.remove(atm1);
        assertNull(st.getByKey(atm1));
        st.put(null, atm1, "Test String1");
        st.put(atm1, new IntegerTreeKey(2), "Test String1");
        st.remove(new IntegerTreeKey(2));
        assertEquals(st.getByKey(new IntegerTreeKey(2)).size(), 0);
    }

    @Test
    public void testRemoveChildren() {
        ITreeContainer<AbstractTreeManager, String> st = new TreeContainer<>();
        AbstractTreeManager atm1 = new IntegerTreeKey(1);
        AbstractTreeManager atm2 = new IntegerTreeKey(2);
        AbstractTreeManager atm3 = new IntegerTreeKey(3);
        st.put(null, atm2 , "two");
        st.put(atm2, atm1, "eight");
        st.put(atm2, atm3, "twelve");
        st.put(atm3, atm1, "Message");
        st.put(atm3, atm1, "HELLO");
        st.removeChildren(atm2);
        assertTrue(st.getByKey(atm1).isEmpty());
        assertTrue(st.getByKey(atm3).isEmpty());
    }

    @Test
    public void testIsEmpty() {
        ITreeContainer<AbstractTreeManager, String> st = new TreeContainer<>();
        assertTrue(st.isEmpty());
        st.put(null, new IntegerTreeKey(2) , "two");
        assertFalse(st.isEmpty());
    }

    @Test
    public void testGetSubTree() {
        ITreeContainer<AbstractTreeManager, String> st = new TreeContainer<>();
        st.put(null, new IntegerTreeKey(1) , "one");
        st.put(new IntegerTreeKey(1), new IntegerTreeKey(2), "HELLO");
        st.put(new IntegerTreeKey(1), new IntegerTreeKey(5), "twelve");
        st.put(new IntegerTreeKey(2), new IntegerTreeKey(7), "Message");

        ITreeContainer<AbstractTreeManager, String> st1 = st.getSubTree(new IntegerTreeKey(1));
        assertEquals(st1.getByKey(new IntegerTreeKey(1)).size(), 1);
        assertEquals(st1.getByKey(new IntegerTreeKey(2)).size(), 1);
        assertEquals(st1.getByKey(new IntegerTreeKey(5)).size(), 1);
        assertEquals(st1.getByKey(new IntegerTreeKey(7)).size(), 1);

        st.remove(new IntegerTreeKey(2));
        assertEquals(st1.getByKey(new IntegerTreeKey(2)).size(), 1);

        st1.remove(new IntegerTreeKey(1));
        assertEquals(st.getByKey(new IntegerTreeKey(1)).size(), 1);
    }
}
