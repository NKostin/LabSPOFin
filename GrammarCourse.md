Грамматика курсовой работы:
lang: (expr)*
expr: declare | assign | for_loop | switcher
declare: VAR_KW WS VAR SM
assign: VAR ASSIGN_OP| LESS_OP|MORE_OP|LESSOREQ_OP
|MOREOREQ_OP smth SM
smth: smth_unit (PLUS| MINUS |DEL| UMN smth_unit)*
sth_unit: OPENBR smth DIGIT | VAR (CLOSEBR)*
for_loop: for_decl for_body
for_decl: FOR_KW OPENBR (assign)* for_incdec
for_body: OPENBRECES (assign)* CLOSEBRECES
for_incdec: VAR INC| DEC CLOSEBR
switcher: switcher_decl switcher_body
switcher_decl: SWITCHER_KW OPENBR VAR CLOSEBR
switcher_body: OPENBRECES (exprSwi)* defaultSwi 
CLOSEBRECES
exprSwi:  CASE_KW DIGIT CL ASSIGN BREAK_KW SM
defaultSwi:  DEFAULT_KW CL BREAK_KW SM

Терминалы:
VAR_KW:  ^var$ 
WS: ^\\s*$
VAR: ^[a-zA-Z]+$*
DIGIT: ^0|[1-9]{1}[0-9]*$
SM: ^;$
ASSIGN_OP: ^=$
OPENBR: ^[)]$
CLOSEBR: ^[(]$   
PLUS: ^[+]$
MINUS: ^[-]$
DEL: ^[/]$
UMN: ^[*]$
LESS_OP: ^[<]$
MORE_OP: ^[>]$
LESSOREQ_OP: ^[<=]$
MOREOREQ_OP: ^[>=]$
FOR_KW: ^for$
INC: ^[+]{2}$
DEC : ^[-]{2}$
SWITCH_KW : ^switch$
CASE_KW : ^case$
DEFAULT_KW : ^default$
BREAK_KW: ^break$
OPENBRECES : ^[}]$
CLOSEBRECES: ^[{]$
CL: ^[:]$
