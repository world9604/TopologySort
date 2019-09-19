import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.IntStream;

public class Main {
    /**
     * @참고 : http://blog.naver.com/PostView.nhn?blogId=ndb796&logNo=221236874984&parentCategoryNo=&categoryNo=128&viewDate=&isShowPopularPosts=false&from=postView
     * @제목 : 위상 정렬(TopologySort) 구현
     */
    public static void main(String[] args) {
        int num = 7;
        int[] inDegree = new int[num + 1];
        List<Integer>[] nodes = new ArrayList[num + 1];

        nodes[1] = new ArrayList<>();
        nodes[1].add(2);
        inDegree[2]++;
        nodes[1].add(5);
        inDegree[5]++;

        nodes[2] = new ArrayList<>();
        nodes[2].add(3);
        inDegree[3]++;

        nodes[3] = new ArrayList<>();
        nodes[3].add(4);
        inDegree[4]++;

        nodes[4] = new ArrayList<>();
        nodes[4].add(6);
        inDegree[6]++;

        nodes[5] = new ArrayList<>();
        nodes[5].add(6);
        inDegree[6]++;

        nodes[6] = new ArrayList<>();
        nodes[6].add(7);
        inDegree[7]++;

        int[] result = new TopologySort(nodes, inDegree).start(num);
        System.out.println();
        IntStream.of(result).forEach(value -> System.out.printf("%d ", value));
    }
}

class TopologySort {
    private List<Integer>[] nodeList;
    private int[] inDegree;

    TopologySort(List<Integer>[] nodeList, int[] inDegree) {
        this.nodeList = nodeList;
        this.inDegree = inDegree;
    }

    int[] start(int num) {
        int[] result = new int[num];
        Queue<Integer> queue = new LinkedList();

        // 진입 차수가 0인 노드를 큐에 삽입합니다.
        for (int i = 1; i <= num; i++) {
            if (inDegree[i] == 0) queue.add(i);
        }

        // 정렬이 완전히 수행되려면 정확히 n개의 노드를 방문합니다.
        for (int i = 0; i < num; i++) {
            // n개를 방문하기 전에 큐가 비어버리면 사이클이 발생한 것입니다.
            if (queue.isEmpty()) {
                System.out.printf("사이클이 발생 했습니다.");
                return null;
            }

            int cur = queue.poll();
            result[i] = cur;
            for (int k = 0; cur < num && k < nodeList[cur].size(); k++) {
                int y = nodeList[cur].get(k);
                // 현재 노드와 연결 되어 있던 노드의 진입 차수를 뺀다.
                inDegree[y]--;
                // 새롭게 진입 차수가 0이 된 정점을 큐에 삽입 합니다.
                if (inDegree[y] == 0) {
                    queue.add(y);
                }
            }
        }
        return result;
    }
}