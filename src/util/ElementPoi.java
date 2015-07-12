package util;

import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.PhpModifierList;
import navigation.exception.MissingRequiredChildException;
import org.jetbrains.annotations.NotNull;

/**
 * @author Artur.Safin <treilor@gmail.com>
 * @licence MIT
 */
public class ElementPoi
{
    private final static Class[] poiClasses = {PhpModifierList.class};

    private static PsiElement findBack(@NotNull PsiElement pivot, boolean deep)
    {
        return findBack(pivot, deep, true, false);
    }

    private static PsiElement findBack(@NotNull PsiElement pivot, boolean deep, boolean rise, boolean selfInclusive)
    {
        PsiElement prev = ElementUtil.getPrevOfType(pivot, poiClasses, selfInclusive);
        if (prev == null) { // Not found on current layer
            PsiElement parent = pivot.getParent();
            if (rise && parent != null) {
                return findBack(parent, false, true, true);
            } else {
                return null;
            }
        } else if (deep) {
            PsiElement lastChild = prev.getLastChild();
            if (lastChild != null) {
                return findBack(lastChild, true, false, true);
            } else {
                return prev;
            }
        } else {
            return prev;
        }
    }

    public static PsiElement requireBack(PsiElement pivot, boolean deep) throws MissingRequiredChildException {
        PsiElement element = findBack(pivot, deep);
        if (element == null) {
            throw new MissingRequiredChildException();
        }
        return element;
    }

    private static PsiElement findForward(PsiElement pivot, boolean deep)
    {
        PsiElement firstChild = pivot.getFirstChild();
        if (deep && firstChild != null) {
            return firstChild;
        }

        return pivot.getNextSibling();
    }

    public static PsiElement requireForward(PsiElement pivot, boolean deep) throws MissingRequiredChildException {
        PsiElement element = findForward(pivot, deep);
        if (element == null) {
            throw new MissingRequiredChildException();
        }
        return element;
    }
}
