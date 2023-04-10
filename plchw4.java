

class Parser {
    private List<String> tokens;
    private int pos;
  
    public Parser(List<String> tokens) {
      this.tokens = tokens;
      pos = 0;
    }
  
    public boolean parse() {
      return stmt();
    }
  
    private boolean stmt() {
      if (ifStmt() || block() || expr() || whileLoop()) {
        return true;
      }
      return false;
    }
  
    private boolean stmtList() {
      if (match("{")) {
        while (!match("}")) {
          if (!stmt()) {
            return false;
          }
          if (!match(";")) {
            return false;
          }
        }
        return true;
      }
      return false;
    }
  
    private boolean whileLoop() {
      if (match("while")) {
        if (match("(")) {
          if (!boolExpr()) {
            return false;
          }
          if (match(")")) {
            if (stmt()) {
              if (match(";")) {
                return true;
              }
            } else if (block()) {
              return true;
            }
          }
        }
      }
      return false;
    }
  
    private boolean ifStmt() {
      if (match("if")) {
        if (match("(")) {
          if (!boolExpr()) {
            return false;
          }
          if (match(")")) {
            if (stmt()) {
              if (match(";")) {
                if (match("else")) {
                  if (stmt()) {
                    if (match(";")) {
                      return true;
                    }
                  } else if (block()) {
                    return true;
                  }
                } else {
                  return true;
                }
              }
            } else if (block()) {
              if (match("else")) {
                if (stmt()) {
                  if (match(";")) {
                    return true;
                  }
                } else if (block()) {
                  return true;
                }
              } else {
                return true;
              }
            }
          }
        }
      }
      return false;
    }
  
    private boolean block() {
      if (match("{")) {
        if (stmtList()) {
          if (match("}")) {
            return true;
          }
        }
      }
      return false;
    }
  
    private boolean expr() {
      if (term()) {
        while (match("+") || match("-")) {
          if (!term()) {
            return false;
          }
        }
        return true;
      }
      return false;
    }
  
    private boolean term() {
      if (fact()) {
        while (match("*") || match("/") || match("%")) {
          if (!fact()) {
            return false;
          }
        }
        return true;
      }
      return false;
    }
  
    private boolean fact() {
      if (match("ID") || match("INT_LIT") || match("FLOAT_LIT")) {
        return true;
      } else if (match("(")) {
        if (!expr()) {
          return false;
        }
        if (match(")")) {
          return true;
        }
      }
      return false;
    }
  
    private boolean boolExpr() {
      if (bterm()) {
        while (match(">") || match("<") || match(">=") || match("<=")) {
          if (!bterm()) {
            return false;
          }
        }
        return
  