package util;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiWhiteSpace;
import com.jetbrains.php.lang.documentation.phpdoc.psi.PhpDocComment;
import com.jetbrains.php.lang.psi.elements.Field;
import com.jetbrains.php.lang.psi.elements.PhpClass;

import java.util.Arrays;

/**
 * @author Artur.Safin <treilor@gmail.com>
 * @licence MIT
 */
public class ElementUtil
{
    private static boolean isOfTypes(Class<?>[] types, Object o)
    {
        for (Class<?> t: types) {
            if (t.isInstance(o)) {
                return true;
            }
        }
        return false;
    }

    public static PsiElement getPrevOfType(PsiElement e, Class<? extends PsiElement>[] types, boolean checkSelf)
    {
        if (checkSelf && isOfTypes(types, e)) {
            return e;
        }

        for (PsiElement sibling = e.getPrevSibling(); sibling != null; sibling = sibling.getPrevSibling()) {
            if (isOfTypes(types, sibling)) {
                return sibling;
            }
        }
        return null;
    }

    public static PsiElement getNextOfType(PsiElement e, Class<? extends PsiElement>[] types, boolean checkSelf)
    {
        if (checkSelf && isOfTypes(types, e)) {
            return e;
        }

        for (PsiElement sibling = e.getNextSibling(); sibling != null; sibling = sibling.getNextSibling()) {
            if (isOfTypes(types, sibling)) {
                return sibling;
            }
        }
        return null;
    }

    public static PsiElement getNextNotWhitespace(PsiElement e)
    {
        for(PsiElement sibling = e.getNextSibling(); sibling != null; sibling = sibling.getNextSibling()) {
            if(!(sibling instanceof PsiWhiteSpace)) {
                return sibling;
            }
        }
        return null;
    }

    public static int getBeginOffset(PhpClass el)
    {
        int baseIndex = el.getStartOffsetInParent();
        String text = el.getText();

        int openingBracket = text.indexOf("{");
        if (openingBracket >= 0) {
            int newLine = text.indexOf("\n", openingBracket);
            if (newLine >= 0) {
                return baseIndex + newLine;
            } else {
                return baseIndex + openingBracket;
            }
        } else {
            return baseIndex;
        }
    }

    public static int getBeginOffset(PsiElement el)
    {
        return el.getTextOffset();
    }

    public static int getEndOffset(PhpDocComment el)
    {
        int textPos;

        textPos = el.getText().lastIndexOf("\n");
        if (textPos == -1) {
            textPos = el.getText().lastIndexOf("\r");
        }
        if (textPos == -1) {
            textPos = el.getTextLength() - 3;
        }

        return el.getTextOffset() + textPos;
    }

    public static int getEndOffset(Field field)
    {
        PsiFile psiFile = field.getContainingFile();

        int baseIndex = field.getTextOffset() + field.getTextLength();
        int semicolonIndex = psiFile.getText().indexOf(";", baseIndex);

        if (semicolonIndex >= 0) {
            PsiElement nextSibling = getNextNotWhitespace(field);
            if (nextSibling == null || semicolonIndex <= nextSibling.getTextOffset()) {
                return semicolonIndex + 1;
            } else {
                return baseIndex;
            }
        } else {
            return baseIndex;
        }
    }
}
