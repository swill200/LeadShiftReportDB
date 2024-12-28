package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class Toast {
    private Shell toastShell;

    public Toast(Shell parent, String message) {
        // Create a non-focusable, top-level shell for the toast
        toastShell = new Shell(parent, SWT.ON_TOP | SWT.NO_FOCUS | SWT.TOOL);
        toastShell.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_INFO_BACKGROUND));
        toastShell.setLayout(null);

        // Add a label for the message
        Label label = new Label(toastShell, SWT.NONE);
        label.setText(message);
        label.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_INFO_BACKGROUND));
        label.setBounds(10, 10, 280, 30);

        // Set the size of the toast shell
        Point size = toastShell.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        Rectangle parentBounds = parent.getBounds();
        int x = parentBounds.x + (parentBounds.width - size.x) / 2;
        int y = parentBounds.y + (parentBounds.height - size.y) / 2;
        toastShell.setBounds(x, y, size.x, size.y);
    }

    public void showToast() {
        Display display = toastShell.getDisplay();
        toastShell.open();

        // Fade out after .7 seconds
        display.timerExec(700, () -> {
            if (!toastShell.isDisposed()) {
                toastShell.close();
            }
        });
    }
}
