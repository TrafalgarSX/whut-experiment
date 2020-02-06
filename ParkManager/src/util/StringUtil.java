package util;
/**
 * 
 * @author guo
 *String���һЩ���ò�������
 */

public class StringUtil {
  public static boolean isEmpty(String str) {
	if(str==null||"".contentEquals(str)) {
		return true;
	}
	return false;
}
	
}
