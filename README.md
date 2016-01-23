# A Simple Web Stack with Guice problems

**How to run this?**

    cd webapp/
    gradle jettyRunWar

Observe the following error in the terminal:

```
Failed startup of context org.gradle.api.plugins.jetty.internal.JettyPluginWebAppContext@64f49b3{/,/home/maarten/projects/guice-problem/simple-web-stack/webapp/build/libs/webapp-1.0.war}
com.google.inject.CreationException: Unable to create injector, see the following errors:

1) A binding to org.apache.shiro.mgt.SecurityManager was already configured at org.apache.shiro.guice.ShiroModule.configure(ShiroModule.java:71) (via modules: com.abbink.simplewebstack.webapp.di.SwsModule -> com.abbink.simplewebstack.api.di.SwsApiServletModule -> com.abbink.simplewebstack.api.auth.di.ShiroAuthModule).
  at com.abbink.simplewebstack.ui.di.SwsUiServletModule.configureServlets(SwsUiServletModule.java:31) (via modules: com.abbink.simplewebstack.webapp.di.SwsModule -> com.abbink.simplewebstack.ui.di.SwsUiServletModule -> com.abbink.simplewebstack.ui.auth.di.ShiroAuthModule)

2) A binding to org.apache.shiro.guice.web.GuiceShiroFilter was already configured at org.apache.shiro.guice.web.ShiroWebModule.configureShiro(ShiroWebModule.java:124) (via modules: com.abbink.simplewebstack.webapp.di.SwsModule -> com.abbink.simplewebstack.api.di.SwsApiServletModule -> com.abbink.simplewebstack.api.auth.di.ShiroAuthModule).
  at com.abbink.simplewebstack.ui.di.SwsUiServletModule.configureServlets(SwsUiServletModule.java:31) (via modules: com.abbink.simplewebstack.webapp.di.SwsModule -> com.abbink.simplewebstack.ui.di.SwsUiServletModule -> com.abbink.simplewebstack.ui.auth.di.ShiroAuthModule)

3) A binding to org.apache.shiro.mgt.SecurityManager was already configured at org.apache.shiro.guice.ShiroModule.configure(ShiroModule.java:71) (via modules: com.abbink.simplewebstack.webapp.di.SwsModule -> com.abbink.simplewebstack.api.di.SwsApiServletModule -> com.abbink.simplewebstack.api.auth.di.ShiroAuthModule).
  at org.apache.shiro.guice.ShiroModule.configure(ShiroModule.java:55) (via modules: com.abbink.simplewebstack.webapp.di.SwsModule -> com.abbink.simplewebstack.ui.di.SwsUiServletModule -> com.abbink.simplewebstack.ui.auth.di.ShiroAuthModule)

4) A binding to org.apache.shiro.guice.web.GuiceShiroFilter was already configured at org.apache.shiro.guice.web.ShiroWebModule.configureShiro(ShiroWebModule.java:124) (via modules: com.abbink.simplewebstack.webapp.di.SwsModule -> com.abbink.simplewebstack.api.di.SwsApiServletModule -> com.abbink.simplewebstack.api.auth.di.ShiroAuthModule).
  at org.apache.shiro.guice.web.ShiroWebModule.configureShiro(ShiroWebModule.java:123) (via modules: com.abbink.simplewebstack.webapp.di.SwsModule -> com.abbink.simplewebstack.ui.di.SwsUiServletModule -> com.abbink.simplewebstack.ui.auth.di.ShiroAuthModule)

4 errors
	at com.google.inject.internal.Errors.throwCreationExceptionIfErrorsExist(Errors.java:466)
	at com.google.inject.internal.InternalInjectorCreator.initializeStatically(InternalInjectorCreator.java:155)
	at com.google.inject.internal.InternalInjectorCreator.build(InternalInjectorCreator.java:107)
	at com.google.inject.Guice.createInjector(Guice.java:96)
	at com.google.inject.Guice.createInjector(Guice.java:73)
	at com.google.inject.Guice.createInjector(Guice.java:62)
	at com.abbink.simplewebstack.webapp.SwsServletContextListener.getInjector(SwsServletContextListener.java:54)
	at com.google.inject.servlet.GuiceServletContextListener.contextInitialized(GuiceServletContextListener.java:47)
	at com.abbink.simplewebstack.webapp.SwsServletContextListener.contextInitialized(SwsServletContextListener.java:41)
	at org.mortbay.jetty.handler.ContextHandler.startContext(ContextHandler.java:548)
	at org.mortbay.jetty.servlet.Context.startContext(Context.java:136)
	at org.mortbay.jetty.webapp.WebAppContext.startContext(WebAppContext.java:1272)
	at org.mortbay.jetty.handler.ContextHandler.doStart(ContextHandler.java:517)
	at org.mortbay.jetty.webapp.WebAppContext.doStart(WebAppContext.java:489)
	at org.gradle.api.plugins.jetty.internal.JettyPluginWebAppContext.doStart(JettyPluginWebAppContext.java:112)
	at org.mortbay.component.AbstractLifeCycle.start(AbstractLifeCycle.java:50)
	at org.mortbay.jetty.handler.HandlerCollection.doStart(HandlerCollection.java:152)
	at org.mortbay.jetty.handler.ContextHandlerCollection.doStart(ContextHandlerCollection.java:156)
	at org.mortbay.component.AbstractLifeCycle.start(AbstractLifeCycle.java:50)
	at org.mortbay.jetty.handler.HandlerCollection.doStart(HandlerCollection.java:152)
	at org.mortbay.component.AbstractLifeCycle.start(AbstractLifeCycle.java:50)
	at org.mortbay.jetty.handler.HandlerWrapper.doStart(HandlerWrapper.java:130)
	at org.mortbay.jetty.Server.doStart(Server.java:224)
	at org.mortbay.component.AbstractLifeCycle.start(AbstractLifeCycle.java:50)
	at org.gradle.api.plugins.jetty.internal.Jetty6PluginServer.start(Jetty6PluginServer.java:111)
	at org.gradle.api.plugins.jetty.AbstractJettyRunTask.startJettyInternal(AbstractJettyRunTask.java:238)
	at org.gradle.api.plugins.jetty.AbstractJettyRunTask.startJetty(AbstractJettyRunTask.java:191)
	at org.gradle.api.plugins.jetty.AbstractJettyRunTask.start(AbstractJettyRunTask.java:162)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.gradle.internal.reflect.JavaMethod.invoke(JavaMethod.java:63)
	at org.gradle.api.internal.project.taskfactory.AnnotationProcessingTaskFactory$StandardTaskAction.doExecute(AnnotationProcessingTaskFactory.java:218)
	at org.gradle.api.internal.project.taskfactory.AnnotationProcessingTaskFactory$StandardTaskAction.execute(AnnotationProcessingTaskFactory.java:211)
	at org.gradle.api.internal.project.taskfactory.AnnotationProcessingTaskFactory$StandardTaskAction.execute(AnnotationProcessingTaskFactory.java:200)
	at org.gradle.api.internal.AbstractTask$TaskActionWrapper.execute(AbstractTask.java:585)
	at org.gradle.api.internal.AbstractTask$TaskActionWrapper.execute(AbstractTask.java:568)
	at org.gradle.api.internal.tasks.execution.ExecuteActionsTaskExecuter.executeAction(ExecuteActionsTaskExecuter.java:80)
	at org.gradle.api.internal.tasks.execution.ExecuteActionsTaskExecuter.executeActions(ExecuteActionsTaskExecuter.java:61)
	at org.gradle.api.internal.tasks.execution.ExecuteActionsTaskExecuter.execute(ExecuteActionsTaskExecuter.java:46)
	at org.gradle.api.internal.tasks.execution.PostExecutionAnalysisTaskExecuter.execute(PostExecutionAnalysisTaskExecuter.java:35)
	at org.gradle.api.internal.tasks.execution.SkipUpToDateTaskExecuter.execute(SkipUpToDateTaskExecuter.java:64)
	at org.gradle.api.internal.tasks.execution.ValidatingTaskExecuter.execute(ValidatingTaskExecuter.java:58)
	at org.gradle.api.internal.tasks.execution.SkipEmptySourceFilesTaskExecuter.execute(SkipEmptySourceFilesTaskExecuter.java:42)
	at org.gradle.api.internal.tasks.execution.SkipTaskWithNoActionsExecuter.execute(SkipTaskWithNoActionsExecuter.java:52)
	at org.gradle.api.internal.tasks.execution.SkipOnlyIfTaskExecuter.execute(SkipOnlyIfTaskExecuter.java:53)
	at org.gradle.api.internal.tasks.execution.ExecuteAtMostOnceTaskExecuter.execute(ExecuteAtMostOnceTaskExecuter.java:43)
	at org.gradle.api.internal.AbstractTask.executeWithoutThrowingTaskFailure(AbstractTask.java:306)
	at org.gradle.execution.taskgraph.AbstractTaskPlanExecutor$TaskExecutorWorker.executeTask(AbstractTaskPlanExecutor.java:79)
	at org.gradle.execution.taskgraph.AbstractTaskPlanExecutor$TaskExecutorWorker.processTask(AbstractTaskPlanExecutor.java:63)
	at org.gradle.execution.taskgraph.AbstractTaskPlanExecutor$TaskExecutorWorker.run(AbstractTaskPlanExecutor.java:51)
	at org.gradle.execution.taskgraph.DefaultTaskPlanExecutor.process(DefaultTaskPlanExecutor.java:23)
	at org.gradle.execution.taskgraph.DefaultTaskGraphExecuter.execute(DefaultTaskGraphExecuter.java:88)
	at org.gradle.execution.SelectedTaskExecutionAction.execute(SelectedTaskExecutionAction.java:29)
	at org.gradle.execution.DefaultBuildExecuter.execute(DefaultBuildExecuter.java:62)
	at org.gradle.execution.DefaultBuildExecuter.access$200(DefaultBuildExecuter.java:23)
	at org.gradle.execution.DefaultBuildExecuter$2.proceed(DefaultBuildExecuter.java:68)
	at org.gradle.execution.DryRunBuildExecutionAction.execute(DryRunBuildExecutionAction.java:32)
	at org.gradle.execution.DefaultBuildExecuter.execute(DefaultBuildExecuter.java:62)
	at org.gradle.execution.DefaultBuildExecuter.execute(DefaultBuildExecuter.java:55)
	at org.gradle.initialization.DefaultGradleLauncher.doBuildStages(DefaultGradleLauncher.java:149)
	at org.gradle.initialization.DefaultGradleLauncher.doBuild(DefaultGradleLauncher.java:106)
	at org.gradle.initialization.DefaultGradleLauncher.run(DefaultGradleLauncher.java:86)
	at org.gradle.launcher.exec.InProcessBuildActionExecuter$DefaultBuildController.run(InProcessBuildActionExecuter.java:80)
	at org.gradle.launcher.cli.ExecuteBuildAction.run(ExecuteBuildAction.java:33)
	at org.gradle.launcher.cli.ExecuteBuildAction.run(ExecuteBuildAction.java:24)
	at org.gradle.launcher.exec.InProcessBuildActionExecuter.execute(InProcessBuildActionExecuter.java:36)
	at org.gradle.launcher.exec.InProcessBuildActionExecuter.execute(InProcessBuildActionExecuter.java:26)
	at org.gradle.launcher.cli.RunBuildAction.run(RunBuildAction.java:51)
	at org.gradle.internal.Actions$RunnableActionAdapter.execute(Actions.java:169)
	at org.gradle.launcher.cli.CommandLineActionFactory$ParseAndBuildAction.execute(CommandLineActionFactory.java:237)
	at org.gradle.launcher.cli.CommandLineActionFactory$ParseAndBuildAction.execute(CommandLineActionFactory.java:210)
	at org.gradle.launcher.cli.JavaRuntimeValidationAction.execute(JavaRuntimeValidationAction.java:35)
	at org.gradle.launcher.cli.JavaRuntimeValidationAction.execute(JavaRuntimeValidationAction.java:24)
	at org.gradle.launcher.cli.CommandLineActionFactory$WithLogging.execute(CommandLineActionFactory.java:206)
	at org.gradle.launcher.cli.CommandLineActionFactory$WithLogging.execute(CommandLineActionFactory.java:169)
	at org.gradle.launcher.cli.ExceptionReportingAction.execute(ExceptionReportingAction.java:33)
	at org.gradle.launcher.cli.ExceptionReportingAction.execute(ExceptionReportingAction.java:22)
	at org.gradle.launcher.Main.doAction(Main.java:33)
	at org.gradle.launcher.bootstrap.EntryPoint.run(EntryPoint.java:45)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.gradle.launcher.bootstrap.ProcessBootstrap.runNoExit(ProcessBootstrap.java:54)
	at org.gradle.launcher.bootstrap.ProcessBootstrap.run(ProcessBootstrap.java:35)
	at org.gradle.launcher.GradleMain.main(GradleMain.java:23)
```

This happens because both `com.abbink.simplewebstack.api.di.SwsApiServletModule` and `com.abbink.simplewebstack.ui.di.SwsUiServletModule` install a `org.apache.shiro.mgt.SecurityManager`, by installing `com.abbink.simplewebstack.api.auth.di.ShiroAuthModule` and `com.abbink.simplewebstack.ui.auth.di.ShiroAuthModule`, respectively (both `ShiroAuthModule`s extend the `org.apache.shiro.guice.web.ShiroWebModule`, which installs this class).

**Why?**
The reason I am installing two `ShiroWebModule`s is because I want the Jersey resources in the `ui` module to only be secured using one Shiro configuration (one that unserstands password-protecting resources), while all Jersey resources in the `api` module should be be secured using an entirely different Shiro configuration (one that understands only bearer tokens as an authentication mechanism).
