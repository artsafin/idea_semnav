package navigation.finder;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

/**
 * @author Artur.Safin <treilor@gmail.com>
 * @licence MIT
 */
public class BackwardFinder implements Finder
{
    @Nonnull
    private final Class[] classes;

    public BackwardFinder(@Nonnull Class<? extends PsiElement>... classes)
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
        PsiElement parent = el.getParent();

        if (isInstance(parent)) {
            if (el.getTextOffset() <= parent.getTextOffset()) {
                return findRecursive(parent);
            }
        }

        return findRecursive(el);
    }

    private @Nullable PsiElement findRecursive(@Nonnull PsiElement el)
    {
        for (PsiElement siblingEl = el.getPrevSibling(); siblingEl != null; siblingEl = siblingEl.getPrevSibling()) {
            if (isInstance(siblingEl)) {
                return siblingEl;
            }

            Collection<PsiElement> children = PsiTreeUtil.findChildrenOfAnyType(siblingEl, classes);
            if (children.size() > 0) {
                PsiElement last = null;
                for (PsiElement e : children) {
                    last = e;
                }
                return last;
            }
        }

        PsiElement parent = el.getParent();
        if (parent != null && !(parent instanceof PsiFile)) {
            return isInstance(parent) ? parent : findRecursive(parent);
        } else {
            return null;
        }
    }
}
