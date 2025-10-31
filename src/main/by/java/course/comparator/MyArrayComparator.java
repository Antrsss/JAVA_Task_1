package main.by.java.course.comparator;

import main.by.java.course.entity.MyArray;
import main.by.java.course.exception.MyArrayException;
import main.by.java.course.service.operation.impl.MyArrayOperationImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;

public enum MyArrayComparator implements Comparator<MyArray> {
    ID {
        @Override
        public int compare(MyArray o1, MyArray o2) {
            logger.debug("ID comparison result: (id1={} vs id2={})", o1.getId(), o2.getId());
            return o1.getId() - o2.getId();
        }
    },
    MIN_SUM {
        @Override
        public int compare(MyArray o1, MyArray o2) {
            int minValue1 = 0;
            int minValue2 = 0;
            try {
                MyArrayOperationImpl service = new MyArrayOperationImpl();
                minValue1 = service.calculateSum(o1);
                minValue2 = service.calculateSum(o2);
            } catch (MyArrayException e) {
                logger.warn("MyArrayComparator: Can't find min sum of MyArrays");
            }

            logger.debug("MIN_SUM comparison result: (sum1={} vs sum2={})", minValue1, minValue2);

            return Math.min(minValue1, minValue2);
        }
    },
    MAX_SIZE {
        @Override
        public int compare(MyArray o1, MyArray o2) {
            int length1 = o1.getMyArray().length;
            int length2 = o2.getMyArray().length;

            logger.debug("MAX_SIZE comparison result: (length1={} vs length2={})", length1, length2);
            return Math.max(length1, length2);
        }
    };

    private static final Logger logger = LogManager.getLogger();
}
