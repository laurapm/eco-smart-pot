<?php

use Twig\Environment;
use Twig\Error\LoaderError;
use Twig\Error\RuntimeError;
use Twig\Markup;
use Twig\Sandbox\SecurityError;
use Twig\Sandbox\SecurityNotAllowedTagError;
use Twig\Sandbox\SecurityNotAllowedFilterError;
use Twig\Sandbox\SecurityNotAllowedFunctionError;
use Twig\Source;
use Twig\Template;

/* table/browse_foreigners/show_all.twig */
class __TwigTemplate_59d444e0cd9751aef9f914f51fab7daed1d53aa6034653a42d5828aec51b3bfe extends \Twig\Template
{
    public function __construct(Environment $env)
    {
        parent::__construct($env);

        $this->parent = false;

        $this->blocks = [
        ];
    }

    protected function doDisplay(array $context, array $blocks = [])
    {
        // line 1
        if ((twig_test_iterable($this->getAttribute(($context["foreign_data"] ?? null), "disp_row", [])) && (        // line 2
($context["show_all"] ?? null) && ($this->getAttribute(($context["foreign_data"] ?? null), "the_total", []) > ($context["max_rows"] ?? null))))) {
            // line 3
            echo "    <input type=\"submit\" id=\"foreign_showAll\" name=\"foreign_showAll\" value=\"";
            // line 4
            echo _gettext("Show all");
            echo "\">
";
        }
    }

    public function getTemplateName()
    {
        return "table/browse_foreigners/show_all.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  35 => 4,  33 => 3,  31 => 2,  30 => 1,);
    }

    /** @deprecated since 1.27 (to be removed in 2.0). Use getSourceContext() instead */
    public function getSource()
    {
        @trigger_error('The '.__METHOD__.' method is deprecated since version 1.27 and will be removed in 2.0. Use getSourceContext() instead.', E_USER_DEPRECATED);

        return $this->getSourceContext()->getCode();
    }

    public function getSourceContext()
    {
        return new Source("", "table/browse_foreigners/show_all.twig", "C:\\Users\\laura\\OneDrive\\Documents\\UniServerZ\\home\\us_opt1\\templates\\table\\browse_foreigners\\show_all.twig");
    }
}
