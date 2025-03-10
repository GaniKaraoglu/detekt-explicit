package com.github.finbot.explicit.rule

import com.github.finbot.explicit.util.Message
import io.gitlab.arturbosch.detekt.api.*
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.psiUtil.containingClass

class NoImplicitFunctionReturnTypeRule(config: Config) : Rule(config) {

    override val issue = Issue(
        "NoImplicitFunctionReturnType",
        Severity.Warning,
        "Functions and class methods should declare their return types explicitly " +
                "to improve code readability and maintainability.",
        Debt.FIVE_MINS
    )

    override fun visitNamedFunction(function: KtNamedFunction) {
        super.visitNamedFunction(function)

        if (function.isInsideTestClass()) {
            // Do not report tests
            return
        }

        // Check if the function has an explicit return type
        if (function.typeReference == null && !function.hasBlockBody() && !function.isLocal) {
            report(
                CodeSmell(
                    issue,
                    Entity.from(function),
                    failureMessage(function)
                )
            )
        }
    }

    private fun KtNamedFunction.isInsideTestClass(): Boolean {
        return containingClass()?.name?.endsWith("Test") == true
    }

    private fun failureMessage(function: KtNamedFunction): String = buildString {
        append("The function '${Message.functionSignature(function)}' should declare an explicit return type. ")
        append("Implicit (missing) return types make the code harder to read and reason about. ")
        append("Changing the implementation of such function is error-prone and can lead to regressions.")
    }
}

