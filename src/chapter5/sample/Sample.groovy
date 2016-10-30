package chapter5.sample

/**
 * Created by hyungsok7 on 2016. 10. 17..
 */
class Sample {

    /**
     * 재귀호출
     *
     * @param x
     * @return
     */
    static def sum(x) {
        if (x <= 0) {
            return 0
        } else {
            return x + sum(x - 1)
        }
    }

    /**
     * 익숙한 방식
     *
     * @param list
     * @param closure
     * @return
     */
    static def <T> List<T> filter_easy(List<T> list, Closure closure) {
        if (list.isEmpty()) {
            return []
        }
        List<T> out = new ArrayList<T>()
        for (T obj : list) {
            if (closure(obj)) {
                out.add(obj)
            }
        }
        return out
    }

    /**
     * 헤드 테일에 의한 제귀 호출 방식
     *
     * @param list
     * @param closure
     * @return
     */
    static def <T> List<T> filter(List<T> list, Closure closure) {
        if (list.isEmpty()) {
            return []
        } else {
            List<T> out = closure(list.head()) ? [list.head()] : []
            return out + filter(list.tail(), closure)
        }
    }

    static void main(String[] args) {
        println sum(10)
        println filter([1, 2, 3, 4, 5, 6, 7, 8], { x -> x > 3 })
    }
}
