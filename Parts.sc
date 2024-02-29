Parts {
  var indices, cbs;

  *new { ^super.new.make; }

  make {
    indices = Dictionary.new;
    cbs = List.new;
  }

  register {
    arg id, cb;
    indices.put(id, cbs.size);
    cbs.add(cb);
  }

  play {
    arg sel;
    var selectedIds = Set.new;
    var selectedCbs = List.new;
    var exprs = sel.split($,);
    exprs.do({
      arg expr;
      if(expr.contains(":"), {
        var bounds = expr.split($:);
        for(indices.at(bounds[0]), indices.at(bounds[1]), {
          arg id;
          selectedIds.includes(id).not && {
            selectedCbs.add(cbs[id]);
            selectedIds.add(id);
          };
        });
      }, {
        var id = indices.at(expr);
        selectedIds.includes(id).not && {
          selectedCbs.add(cbs[id]);
          selectedIds.add(id);
        };
      });
    });
    selectedCbs.do({
      arg cb;
      cb.value;
    });
  }
}
