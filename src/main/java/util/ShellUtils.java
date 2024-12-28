package util;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Shell;

public class ShellUtils {

    // Centers the child shell over its parent
    public static void centerOnParent(Shell parent, Shell child) {
        if (parent == null || child == null) {
            throw new IllegalArgumentException("Parent and child shells cannot be null.");
        }

        Rectangle parentBounds = parent.getBounds(); // Get parent shell bounds
        Point childSize = child.getSize(); // Get child shell size

        int x = parentBounds.x + (parentBounds.width - childSize.x) / 2;
        int y = parentBounds.y + (parentBounds.height - childSize.y) / 2;

        child.setLocation(x, y); // Set the location of the child shell
    }
}
