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
    var splits = sel.split($,);
    splits.do({
      arg split;
      var id = indices.at(split);
      selectedIds.includes(id).not && {
        selectedCbs.add(cbs[id]);
        selectedIds.add(id);
      };
    });
    selectedCbs.do({
      arg cb;
      cb.value;
    });
  }
}
