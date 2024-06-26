package config;

    import jakarta.servlet.ServletContext;
    import jakarta.servlet.ServletException;
    import org.springframework.web.filter.HiddenHttpMethodFilter;
    import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

    public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

        @Override
        protected Class<?>[] getRootConfigClasses() {
            return null;
        }

        protected Class<?>[] getServletConfigClasses() {
            return new Class[] {AppConfig.class};
        }

        @Override
        @SuppressWarnings("NullableProblems")
        protected String[] getServletMappings() {
            return new String[] {"/"};
        }

        @Override
        @SuppressWarnings("NullableProblems")
        public void onStartup(ServletContext aServletContext) throws ServletException {
            super.onStartup(aServletContext);
            registerHiddenFieldFilter(aServletContext);
        }

        private void registerHiddenFieldFilter(ServletContext aContext) {
            aContext.addFilter("hiddenHttpMethodFilter",
                    new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null ,true, "/*");
        }

    }
