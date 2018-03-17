/**
 * BirdsOfAFeather - A GUI interface for playing BirdsOfAFeather.  
 * Depends on and is useful for testing class <code>BirdsOfAFeatherNode</code>.
 *
 * @author Todd Neller
 * @version 1.0

Copyright (C) 2017 Todd Neller

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

Information about the GNU General Public License is available online at:
  http://www.gnu.org/licenses/
To receive a copy of the GNU General Public License, write to the Free
Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
02111-1307, USA.

 */

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.QuadCurve2D;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class BirdsOfAFeather extends JFrame {
	private static final long serialVersionUID = 292469256050756410L;

	public BirdsOfAFeather() {
		super();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Birds of a Feather");
		add(new BirdsOfAFeatherPanel(), BorderLayout.CENTER);
		pack();
		setVisible(true);
	}

	@SuppressWarnings("serial")
	class BirdsOfAFeatherPanel extends JPanel {
		private boolean reportMoves = true;
		private Random random = new Random();
		private boolean isMovingCard = false; // whether or not piece is currently being moved (i.e. dragged)
		private int pressRow, pressCol; // row, col of mouse press
		private int mouseX, mouseY; // current position of dragging mouse
		private int size = BirdsOfAFeatherNode.DEFAULT_SIZE;
		private BirdsOfAFeatherNode state = new BirdsOfAFeatherNode(367297990, BirdsOfAFeatherNode.DEFAULT_SIZE);
		private Stack<BirdsOfAFeatherNode> stateStack = new Stack<BirdsOfAFeatherNode>();
		private HashMap<Card, Image> images = new HashMap<Card, Image>();
		private String[] imageRankNames = {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};
		private String[] imageSuitNames = {"club", "heart", "spade", "diamond"}; 
		private double preferredImageScale = .25;
		private int marginPx = 20;
		private int imageWidth;
		private int imageHeight;
		private int panelWidth;
		private int panelHeight;
		private boolean initialDeal = true;
		private boolean showPairs = false;
		private double curveScale = .1;
		private double radius = 10;

		BirdsOfAFeatherPanel() {
			// initialize panel
			setBackground(Color.green.darker().darker());
			addMouseListener(new MouseHandler());
			addMouseMotionListener(new MouseMotionHandler());
			addMenuBar();
			requestFocusInWindow();

			// load images
			MediaTracker mt = new MediaTracker(this);
			for (Card card : Card.allCards) {
				String imageName = String.format("images/%s_of_%ss.png", imageRankNames[card.rank], imageSuitNames[card.suit]);
				Image image = getToolkit().getImage(imageName);
				mt.addImage(image, card.getId());
				images.put(card, image);
			}
			try {
				mt.waitForAll();
			} catch (Exception e) {
				System.err.println("Exception while loading image.");
			}

			// scale images
			int originalWidth = images.get(Card.allCards[0]).getWidth(this);
			int originalHeight = images.get(Card.allCards[0]).getHeight(this);
			imageWidth = (int) Math.round(preferredImageScale * originalWidth);
			imageHeight = (int) Math.round(preferredImageScale * originalHeight);
			for (Card card : Card.allCards)
				images.put(card, images.get(card).getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH));

			// set size
			panelWidth = size * imageWidth + (size + 1) * marginPx;
			panelHeight = size * imageHeight + (size + 1) * marginPx;
			setPreferredSize(new Dimension(panelWidth, panelHeight));
			setSize(panelWidth, panelHeight);
			
			// set key bindings
			Action undo = new AbstractAction() {public void actionPerformed(ActionEvent e) {undo();}};
			getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                    java.awt.event.InputEvent.CTRL_DOWN_MASK), "undo");
			getActionMap().put("undo", undo);
			addKeyListener(new KeyHandler());
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.BLUE);
			g2.setStroke(new BasicStroke(2));
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			if (initialDeal) {
				System.out.println(state);
				initialDeal = false;
			}
			// draw each grid cell
			for (int row = 0; row < size; row++)
				for (int col = 0; col < size; col++) {
					Card card = state.grid[row][col];
					if (card != null && (!isMovingCard || pressRow != row || pressCol != col)) {
						int x = marginPx + col * (imageWidth + marginPx);
						int y = marginPx + row * (imageHeight + marginPx);
						g2.drawImage(images.get(card), x, y, this);
					}
				}
			
			// show moving card
			if (isMovingCard) // if piece is moving
				g2.drawImage(images.get(state.grid[pressRow][pressCol]), mouseX - imageWidth / 2, mouseY - imageHeight / 2, this);
			
			double xCenter = panelWidth / 2;
			double yCenter = panelHeight / 2; 
			// show compatible pairs
			if (showPairs) {
				for (int row1 = 0; row1 < size; row1++)
					for (int col1 = 0; col1 < size; col1++) {
						Card card1 = state.grid[row1][col1];
						if (card1 != null)
							for (int row2 = 0; row2 < size; row2++)
								for (int col2 = 0; col2 < size; col2++) {
									Card card2 = state.grid[row2][col2];
									if (card2 != null && card2 != card1 // not same cells
											&& (card1.suit == card2.suit // same suit ... 
											|| Math.abs(card1.rank - card2.rank) <= 1)) { // or same/adjacent rank.) 
										double x1 = marginPx + col1 * (imageWidth + marginPx) + imageWidth / 2;
										double y1 = marginPx + row1 * (imageHeight + marginPx) + imageHeight / 2;
										if (isMovingCard && pressRow == row1 && pressCol == col1) {
											x1 = mouseX;
											y1 = mouseY;
										}
										double x2 = marginPx + col2 * (imageWidth + marginPx) + imageWidth / 2;
										double y2 = marginPx + row2 * (imageHeight + marginPx) + imageHeight / 2;
										if (isMovingCard && pressRow == row2 && pressCol == col2) {
											x2 = mouseX;
											y2 = mouseY;
										}
										double xMid = (x1 + x2) / 2;
										double yMid = (y1 + y2) / 2;
										double dx = x1 - x2;
										double dy = y1 - y2;
										double x3 = xMid - curveScale * dy;
										double y3 = yMid + curveScale * dx;
										double x4 = xMid + curveScale * dy;
										double y4 = yMid - curveScale * dx;										
										double distSqr3 = (x3 - xCenter) *  (x3 - xCenter) + (y3 - yCenter) * (y3 - yCenter);
										double distSqr4 = (x4 - xCenter) *  (x4 - xCenter) + (y4 - yCenter) * (y4 - yCenter);
										if (distSqr4 > distSqr3) {
											x3 = x4;
											y3 = y4;
										}
										g2.draw(new QuadCurve2D.Double(x1, y1, x3, y3, x2, y2));
										g2.fill(new Ellipse2D.Double(x1 - radius, y1 - radius, radius * 2, radius * 2));
										g2.fill(new Ellipse2D.Double(x2 - radius, y2 - radius, radius * 2, radius * 2));
										g2.setColor(Color.ORANGE);
										g2.draw(new Ellipse2D.Double(x1 - radius, y1 - radius, radius * 2, radius * 2));
										g2.draw(new Ellipse2D.Double(x2 - radius, y2 - radius, radius * 2, radius * 2));
										g2.setColor(Color.BLUE);
									}
								}
					}
			}
			

		}

		void newDeal() {
			try {
				int seed = Integer.parseInt(JOptionPane.showInputDialog("Integer seed?"));
				newDeal(seed);
			}
			catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Invalid seed.");
			}
		}
		
		void newDeal(int seed) {
			if (reportMoves)
				System.out.println("Seed: " + seed);
			initialDeal = true;
			state = new BirdsOfAFeatherNode(seed);
			stateStack.clear();
			repaint();
		}
		
		void hint() {
			Searcher searcher = new DepthFirstSearcherNoRepeat();
	    	SearchNode root = (SearchNode) state.clone();
	    	if (root.isGoal()) return;
	    	if (searcher.search(root)) {
	    		SearchNode curr = searcher.goalNode;
	    		while (curr.parent != root)
	    			curr = curr.parent;
	    		JOptionPane.showMessageDialog(null, ((BirdsOfAFeatherNode) curr).prevMove);
	    	}
	    	else {
	    		// unsuccessful search
	    		JOptionPane.showMessageDialog(null, "No solution exists from this state.");
	    	}
		}
		
		void togglePairs() {
			showPairs = !showPairs;
			repaint();
		}
		
		void addMenuBar() {
			// Create new deal items
			JMenuItem newItem = new JMenuItem("New (n)");
			newItem.addActionListener(e -> {newDeal(random.nextInt());});

			JMenuItem newSeedItem = new JMenuItem("New Seed (s)");
			newSeedItem.addActionListener(e -> {newDeal();});
			
			// Hint
			JMenuItem hintItem = new JMenuItem("Hint (h)");
			hintItem.addActionListener(e -> {hint();});
			
			// Undo
			JMenuItem undoItem = new JMenuItem("Undo (u)");
			undoItem.addActionListener(e -> {undo();});			
			
			// Auto Undo
			JMenuItem autoUndoItem = new JMenuItem("Auto Undo (a)");
			undoItem.addActionListener(e -> {autoUndo();});		
			
			// Auto Undo
			JMenuItem pairsItem = new JMenuItem("Toggle Pairs (t)");
			pairsItem.addActionListener(e -> {togglePairs();});	
			
			// Add items to file menu
			JMenu puzzleMenu = new JMenu("Puzzle");
			puzzleMenu.add(newItem);
			puzzleMenu.add(newSeedItem);
			puzzleMenu.add(hintItem);
			puzzleMenu.add(undoItem);
			puzzleMenu.add(autoUndoItem);
			puzzleMenu.add(pairsItem);

			// Add file menu to menu bar and set frame menu bar
			JMenuBar menuBar = new JMenuBar();
			menuBar.add(puzzleMenu);
			setJMenuBar(menuBar);
		}


		// a mouse handler to trigger mouse press/release events
		class MouseHandler extends MouseAdapter {

			@Override
			public void mousePressed(MouseEvent event) {
				requestFocusInWindow();
				pressRow = event.getY() / (panelHeight / size); // discretize source board row, col
				pressCol = event.getX() / (panelWidth / size);
				if (pressRow >= 0 && pressRow < size && pressCol >= 0 && pressCol < size
						&& state.grid[pressRow][pressCol] != null)
					isMovingCard = true; // change state to reflect card motion potential
				else
					isMovingCard = false;
			}

			@Override
			public void mouseReleased(MouseEvent event) {
				requestFocusInWindow();
				isMovingCard = false; // change state to reflect card motion impossibility
				int releaseRow = event.getY() / (panelHeight / size); // discretize destination row, col
				int releaseCol = event.getX() / (panelWidth / size);
				if (pressRow >= 0 && pressRow < size && pressCol >= 0 && pressCol < size
						&& releaseRow >= 0 && releaseRow < size && releaseCol >= 0 && releaseCol < size 
						&& state.isLegalMove(pressRow, pressCol, releaseRow, releaseCol)) {
					stateStack.push(state);
					state = state.makeMove(pressRow, pressCol, releaseRow, releaseCol);
					if (reportMoves) {
						System.out.println(state.prevMove);
						if (state.isGoal()) {
							Stack<String> moves = new Stack<String>();
							BirdsOfAFeatherNode curr = state;
							while (curr.parent != null) {
								moves.push(curr.prevMove);
								curr = (BirdsOfAFeatherNode) curr.parent;
							}
							System.out.print("Solution:");
							while (!moves.isEmpty())
								System.out.print(" " + moves.pop());
							System.out.println();
						}
					}
				}
				else
					isMovingCard = false;
				repaint(); // repaint
			}
		}

		// a mouse motion handler to track card movement and force repainting during movement
		// based on example from http://www.java2s.com/Code/Java/2D-Graphics-GUI/Imagewithmousedragandmoveevent.htm
		class MouseMotionHandler extends MouseMotionAdapter {
			public void mouseDragged(MouseEvent e) {
				mouseX = e.getX(); // track card movement in mouseX, mouseY fields
				mouseY = e.getY();
				repaint(); // force repaint to animate motion
			}
		}

		// a keypress listener that listens for single character hotkeys
		class KeyHandler extends KeyAdapter {
			public void keyTyped(KeyEvent e) {			
				if (e.getKeyChar() == 'u')
					undo();
				if (e.getKeyChar() == 'a')
					autoUndo();
				if (e.getKeyChar() == 'n')
					newDeal(random.nextInt());
				if (e.getKeyChar() == 's')
					newDeal();
				if (e.getKeyChar() == 'h')
					hint();
				if (e.getKeyChar() == 't')
					togglePairs();
			}
		}
		
		void autoUndo() {
			while (!stateStack.isEmpty()) {
				Searcher searcher = new DepthFirstSearcherNoRepeat();
				SearchNode root = (SearchNode) state.clone();
				if (root.isGoal()) return;
				if (searcher.search(root))
					break;
				else
					undo();
			}
		}
		
		void undo() {
			if (!stateStack.isEmpty()) {
				if (reportMoves)
					System.out.println("undo");
				state = stateStack.pop();
				repaint();
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new BirdsOfAFeather();
	}
}