package by.zgirskaya.course.comparator;

import by.zgirskaya.course.entity.CustomArray;
import by.zgirskaya.course.exception.CustomArrayException;
import by.zgirskaya.course.service.operation.impl.CustomArrayOperationImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;

public enum CustomArrayComparator implements Comparator<CustomArray> {
    ID {
        @Override
        public int compare(CustomArray arr1, CustomArray arr2) {
            logger.debug("ID comparison result: (id1={} vs id2={})", arr1.getId(), arr2.getId());
            return arr1.getId() - arr2.getId();
        }
    },
    MIN_SUM {
        @Override
        public int compare(CustomArray arr1, CustomArray arr2) {
            if (arr1 == null && arr2 == null) { return 0; }
            if (arr1 == null) { return -1; }
            if (arr2 == null) { return 1; }

            int minValue1 = 0;
            int minValue2 = 0;

            try {
                CustomArrayOperationImpl service = new CustomArrayOperationImpl();
                minValue1 = service.calculateSum(arr1);
                minValue2 = service.calculateSum(arr2);
            } catch (CustomArrayException e) {
                logger.warn("MyArrayComparator: Can't find min sum of MyArrays");
            }

            logger.debug("MIN_SUM comparison result: (sum1={} vs sum2={})", minValue1, minValue2);

            return Math.min(minValue1, minValue2);
        }
    },
    MAX_SIZE {
        @Override
        public int compare(CustomArray arr1, CustomArray arr2) {
            int length1 = arr1.getMyArray().length;
            int length2 = arr2.getMyArray().length;

            logger.debug("MAX_SIZE comparison result: (length1={} vs length2={})", length1, length2);
            return Math.max(length1, length2);
        }
    };

    private static final Logger logger = LogManager.getLogger();
}
