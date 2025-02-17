package com.finbot.explicit

import com.finbot.explicit.rule.*
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class FinBotExplicitRuleSetProvider : RuleSetProvider {
    override val ruleSetId: String = "FinBotExplicit"

    override fun instance(config: Config): RuleSet {
        return RuleSet(
            ruleSetId,
            listOf(
                DataClassFunctionsRule(config),
                DataClassDefaultValuesRule(config),
                DataClassTypedIDsRule(config),
                NoImplicitFunctionReturnTypeRule(config),
                UnnecessaryPassThroughClassRule(config),
            ),
        )
    }
}
