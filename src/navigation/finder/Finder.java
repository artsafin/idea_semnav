package navigation.finder;

import com.intellij.psi.PsiElement;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Artur.Safin <treilor@gmail.com>
 * @licence MIT
 */
public interface Finder
{
    public @Nullable PsiElement next(@Nonnull PsiElement el);
}
