package navigation.finder.matcher;

import com.intellij.psi.PsiElement;

import javax.annotation.Nonnull;

/**
 * @author Artur.Safin <treilor@gmail.com>
 * @licence MIT
 */
public class ParentClassMatcher implements PsiElementMatcher
{
    @Nonnull
    private final Class<? extends PsiElement> parentClass;

    public ParentClassMatcher(@Nonnull Class<? extends PsiElement> parentClass)
    {
        this.parentClass = parentClass;
    }

    @Override
    public boolean matches(PsiElement e)
    {
        return e.getParent() != null && parentClass.isInstance(e.getParent());
    }
}
