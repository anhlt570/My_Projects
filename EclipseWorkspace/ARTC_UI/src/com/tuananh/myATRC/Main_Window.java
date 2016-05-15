package com.tuananh.myATRC;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import swing2swt.layout.BoxLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Combo;

public class Main_Window {

	protected Shell shell;
	private Label lblNewLabel;
	private Label lblNewLabel_1;
	private Label lblNewLabel_2;
	private Label lblNewLabel_3;
	private Button btnResult;
	private Button btnExit;
	private Label lblEvolutionInvarants;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Label lblInitialInvarants;
	private Text tIniInvarants;
	private Text tIniPreconditions;
	private Text tIniPostconditions;
	private Text tEvoPreconditions;
	private Text tEvoInvarants;
	private Text tEvoPostconditions;
	private Text tResult;
	private Button btnCheck;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Main_Window window = new Main_Window();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		Validation val = new Validation();
		Reader tr= new Reader();
		shell = new Shell();
		shell.setMinimumSize(new Point(800, 600));
		shell.setSize(800, 577);
		shell.setText("SWT Application");
		shell.setLayout(new GridLayout(4, false));
		
		lblInitialInvarants = new Label(shell, SWT.NONE);
		lblInitialInvarants.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblInitialInvarants.setText("Initial Invarants");
		formToolkit.adapt(lblInitialInvarants, true, true);
		
		tIniInvarants = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		GridData gd_tIniInvarants = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_tIniInvarants.heightHint = 107;
		gd_tIniInvarants.widthHint = 207;
		tIniInvarants.setLayoutData(gd_tIniInvarants);
		formToolkit.adapt(tIniInvarants, true, true);
		
		lblEvolutionInvarants = new Label(shell, SWT.NONE);
		lblEvolutionInvarants.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEvolutionInvarants.setText("Evolution Invarants");
		
		tEvoInvarants = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		GridData gd_tEvoInvarants = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_tEvoInvarants.heightHint = 107;
		tEvoInvarants.setLayoutData(gd_tEvoInvarants);
		formToolkit.adapt(tEvoInvarants, true, true);
		
		lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Initial Preconditions");
		
		tIniPreconditions = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		GridData gd_tIniPreconditions = new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1);
		gd_tIniPreconditions.heightHint = 149;
		gd_tIniPreconditions.widthHint = 207;
		tIniPreconditions.setLayoutData(gd_tIniPreconditions);
		formToolkit.adapt(tIniPreconditions, true, true);
		
		lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("Evolution Preconditions");
		
		tEvoPreconditions = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		tEvoPreconditions.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		formToolkit.adapt(tEvoPreconditions, true, true);
		
		lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_2.setText("Initial Postconditions");
		
		tIniPostconditions = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		GridData gd_tIniPostconditions = new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1);
		gd_tIniPostconditions.heightHint = 149;
		gd_tIniPostconditions.widthHint = 207;
		tIniPostconditions.setLayoutData(gd_tIniPostconditions);
		formToolkit.adapt(tIniPostconditions, true, true);
		
		lblNewLabel_3 = new Label(shell, SWT.NONE);
		lblNewLabel_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_3.setText("Evolution Postconditions");
		
		tEvoPostconditions = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		tEvoPostconditions.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		formToolkit.adapt(tEvoPostconditions, true, true);
		
		btnResult = new Button(shell, SWT.NONE);
		btnResult.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnResult.setText("Result");
		btnResult.addSelectionListener(new SelectionAdapter() 
		{
			public void widgetSelected(SelectionEvent e)
			{
				String tmp1= tIniInvarants.getText();
				String tmp2= tEvoInvarants.getText();
				//tResult.setText("SATISFIABLE");
				if(val.checkInvarants(tmp1, tmp2)) 
				{
					tResult.setText("SATISFIABLE");
				}
				else
				{
					tResult.setText("UN_SATISFIABLE");
				}
			}
		});
		
		tResult = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		GridData gd_tResult = new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1);
		gd_tResult.heightHint = 60;
		tResult.setLayoutData(gd_tResult);
		formToolkit.adapt(tResult, true, true);
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem miFile = new MenuItem(menu, SWT.NONE);
		miFile.setText("File");
		
		MenuItem miAbout = new MenuItem(menu, SWT.NONE);
		miAbout.setText("About");
		Label label = new Label(shell, SWT.NONE);
		formToolkit.adapt(label, true, true);
		
		btnCheck = new Button(shell, SWT.NONE);
		btnCheck.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				String tmp= tEvoPostconditions.getText();
				tmp= tr.standardizedText(tmp);
				tIniPostconditions.setText(tmp);
			}
		});
		formToolkit.adapt(btnCheck, true, true);
		btnCheck.setText("Check");
		new Label(shell, SWT.NONE);
		
		btnExit = new Button(shell, SWT.PUSH);
		GridData gd_btnExit = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 0, 1);
		gd_btnExit.horizontalIndent = 1;
		btnExit.setLayoutData(gd_btnExit);
		btnExit.setAlignment(SWT.LEFT);
		btnExit.setText("     Exit     ");
		btnExit.addSelectionListener(new SelectionAdapter() 
		{
			public void widgetSelected(SelectionEvent e)
			{
				System.exit(0);
			}
		});
	}
}
