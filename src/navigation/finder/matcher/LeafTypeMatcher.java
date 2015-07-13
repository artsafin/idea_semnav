package navigation.finder.matcher;

import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.LeafPsiElement;

import javax.annotation.Nullable;

/**
 * @author Artur.Safin <treilor@gmail.com>
 * @licence MIT
 */
public class LeafTypeMatcher implements PsiElementMatcher
{
    private final String elementType;

    public LeafTypeMatcher(String elementType)
    {
        this.elementType = elementType;
    }

    @Override
    public boolean matches(PsiElement e)
    {
        return (e instanceof LeafPsiElement) && ((LeafPsiElement) e).getElementType().toString().equals(elementType);
    }
}
