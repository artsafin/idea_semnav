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
public class DeepBackwardFinder implements Finder
{
    @Nonnull
    private final PsiElementMatcher matcher;
    @Nonnull
    private final PsiElementMatcher stop;

    public DeepBackwardFinder(@Nonnull PsiElementMatcher matcher, @Nonnull PsiElementMatcher stop)
    {
        this.matcher = matcher;
        this.stop = stop;
    }

    @Nullable
    @Override
    public PsiElement next(@Nonnull PsiElement el)
    {
        do {
            if (el.getPrevSibling() != null) {
                PsiElement match = findMatchInSiblingsOrDepth(el.getPrevSibling());

                if (match != null) {
                    return match;
                }
            }
            el = el.getParent();
            if (matcher.matches(el)) {
                return el;
            }
        } while (!stop.matches(el));

        return null;
    }

    @Nullable
    private PsiElement findMatchInSiblingsOrDepth(PsiElement el)
    {
        for (PsiElement prevEl = el; prevEl != null; prevEl = prevEl.getPrevSibling()) {
            PsiElement match = findMatchInSiblingsOrDepth(prevEl.getLastChild());
            if (match != null) {
                return match;
            } else if (matcher.matches(prevEl)) {
                return prevEl;
            }
        }

        return null;
    }
}
