import java.util.ArrayList;
import java.util.List;

public class Test {
	public static class Model {
		private List<String> list;

		public List<String> getList() {
			return list;
		}

		public void setList(List<String> list) {
			this.list = list;
		}
	}
	
	public static void main(String[] args) {
		Model model=new Model();
		List<String> list = new ArrayList<>();
		list.add("1");
		list.add("2");
		list.add("3");
		model.setList(list);
		model.getList().forEach(action->System.out.println(action));
	}


}
