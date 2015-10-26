public class Exceptions {
	public void test1() {
		try {
			test2("hallo");
		} catch (Exception e) {
			// Den Stacktrace (Liste der Caller bis zum Auftreten der Exception)
			e.printStackTrace();
		}
	}

	public void test2(String hallo) throws Exception {
		if (!"hallo".equals(hallo)) {
			throw new Exception("The String MUST be \"hallo\"!");
		}
	}
}
