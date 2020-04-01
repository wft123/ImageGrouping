package com.roxy.image;

public class Main {
	public static void main(String[] args) throws Exception {
		
		ImageGrouping ig = new ImageGrouping();

		if(args.length < 2 ) {
			GroupUI frame = new GroupUI(ig);
			
			frame.pack();
			frame.setSize(400, 250);
			frame.setVisible(true);
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
		} else {
			ig.startGrouping(args[0], args[1], "move".equalsIgnoreCase(args[2]));
		}
		
	}
}