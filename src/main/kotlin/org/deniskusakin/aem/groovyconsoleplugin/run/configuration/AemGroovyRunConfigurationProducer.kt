package org.deniskusakin.aem.groovyconsoleplugin.run.configuration

import com.intellij.execution.actions.ConfigurationContext
import com.intellij.execution.actions.RunConfigurationProducer
import com.intellij.execution.configurations.ConfigurationType
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiElement
import org.deniskusakin.aem.groovyconsoleplugin.services.PersistentStateService

class AemGroovyRunConfigurationProducer private constructor() : RunConfigurationProducer<AemGroovyRunConfiguration>(AemGroovyConfigurationType()) {
    override fun isConfigurationFromContext(configuration: AemGroovyRunConfiguration, context: ConfigurationContext): Boolean {
//        if (context.location?.virtualFile?.path == configuration.scriptPath) {
////            return true
////        }
        return false
    }

    override fun setupConfigurationFromContext(configuration: AemGroovyRunConfiguration, context: ConfigurationContext, sourceElement: Ref<PsiElement>?): Boolean {
        val service = ServiceManager.getService(context.project, PersistentStateService::class.java)
        val firstServerFromGlobalConfig = service.getAEMServers().firstOrNull()
        if (context.location?.virtualFile?.extension == "groovy" && firstServerFromGlobalConfig != null) {
            configuration.scriptPath = context.location?.virtualFile?.path
            configuration.serverName = firstServerFromGlobalConfig.name
//            configuration.serverHost = "http://localhost:4502"
//            configuration.login = "admin"
//            configuration.password = "admin"
            configuration.name = "${firstServerFromGlobalConfig.name}:${context.location?.virtualFile?.name}"

            return true
        }
        return false
    }
}