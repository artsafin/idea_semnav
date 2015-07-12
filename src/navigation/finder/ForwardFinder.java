package navigation.finder;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Artur.Safin <treilor@gmail.com>
 * @licence MIT
 */
public class ForwardFinder implements Finder
{
    @Nonnull
    private final Class[] classes;

    public ForwardFinder(@Nonnull Class<? extends PsiElement>... classes)
    {
        this.classes = classes;
    }

    private boolean isInstance(PsiElement el)
    {
        for (Class c: classes) {
            if (c.isInstance(el)) {
                return true;
            }
        }

        return false;
    }

    public @Nullable PsiElement next(@Nonnull PsiElement el)
    {
        /*
        PsiElement parent = el.getParent();

        if (isInstance(parent)) {
            if (el.getTextOffset() <= parent.getTextOffset()) {
                return findRecursive(parent);
            }
        }
        */

        return findRecursive(el);
    }

    private @Nullable PsiElement findRecursive(@Nonnull PsiElement el)
    {
        for (PsiElement siblingEl = el.getNextSibling(); siblingEl != null; siblingEl = siblingEl.getNextSibling()) {
            if (isInstance(siblingEl)) {
                return siblingEl;
            }

            Iterator iterator = PsiTreeUtil.findChildrenOfAnyType(siblingEl, classes).iterator();
            if (iterator.hasNext()) {
                return (PsiElement) iterator.next();
            }
        }

        return null;
    }
}
