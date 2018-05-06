package team6.entity;

public enum BedType {
	KING_BED,QUEEN_BED,DOUBLE_BED,SUITE;
	
	public String getDisplayString() {
		String str = this.toString().toLowerCase();
		String[] split = str.split("_");
		for(int i = 0; i < split.length; i++) {
			if(split[i].length() == 1) {
				split[i] = split[i].toUpperCase();
			}
			else {
				split[i] = split[i].toLowerCase();
				split[i] = split[i].substring(0, 1).toUpperCase() + split[i].substring(1);
			}
		}
		str = "";
		for(int i = 0; i < split.length; i++) {
			str += split[i];
			if(i < split.length - 1) {
				str += " ";
			}
		}
		return str;
	}
}
