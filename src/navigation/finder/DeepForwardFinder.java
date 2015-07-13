package navigation.finder;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import navigation.finder.matcher.PsiElementMatcher;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Artur.Safin <treilor@gmail.com>
 * @licence MIT
 */
public class DeepForwardFinder implements Finder
{
    @Nonnull
    private final PsiElementMatcher matcher;
    @Nonnull
    private final PsiElementMatcher stop;

    public DeepForwardFinder(@Nonnull PsiElementMatcher matcher, @Nonnull PsiElementMatcher stop)
    {
        this.matcher = matcher;
        this.stop = stop;
    }

    private boolean offsetsNotEqual(PsiElement a, PsiElement b) {
        return a.getTextOffset() != b.getTextOffset();
    }

    @Nullable
    @Override
    public PsiElement next(@Nonnull PsiElement el)
    {
        el = (el.getFirstChild() == null) ? el : el.getFirstChild();

        do {
            if (el.getNextSibling() != null) {
                PsiElement match = findMatchInSiblingsOrDepth(el.getNextSibling());
                if (match != null) {
                    return match;
                }
            }

            el = el.getParent();
        } while (!stop.matches(el));

        return null;
    }

    @Nullable
    private PsiElement findMatchInSiblingsOrDepth(PsiElement el)
    {
        for (PsiElement nextEl = el; nextEl != null; nextEl = nextEl.getNextSibling()) {
            if (matcher.matches(nextEl)) {
                return nextEl;
            }
            PsiElement match = findMatchInSiblingsOrDepth(nextEl.getFirstChild());
            if (match != null) {
                return match;
            }
        }

        return null;
    }
}
