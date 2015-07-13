package navigation.finder.matcher;

import com.intellij.psi.PsiElement;

import javax.annotation.Nonnull;

/**
 * @author Artur.Safin <treilor@gmail.com>
 * @licence MIT
 */
public class ClassMatcher implements PsiElementMatcher
{
    @Nonnull
    private final Class[] classes;

    public ClassMatcher(@Nonnull Class... classes)
    {
        this.classes = classes;
    }

    @Override
    public boolean matches(PsiElement e)
    {
        for (Class c : classes) {
            if (c.isInstance(e)) {
                return true;
            }
        }

        return false;
    }
}
