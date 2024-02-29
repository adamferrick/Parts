Parts {
  var indices, identifiers, cbs;

  *new { ^super.new.make; }

  make {
    indices = Dictionary.new;
    identifiers = List.new;
    cbs = List.new;
  }

  register {
    arg id, cb;
    indices.put(id, cbs.size);
    identifiers.add(id);
    cbs.add(cb);
  }

  playAll {
    cbs.do({
      arg cb;
      cb.value;
    });
  }

  play {
    arg ... sels;
    var selectedIds = Set.new;
    var selectedCbs = List.new;
    sels.do({
      arg sel;
      if(sel.contains(":"), {
        var bounds = sel.split($:);
        for(indices.at(bounds[0]), indices.at(bounds[1]), {
          arg id;
          selectedIds.includes(id).not && {
            selectedCbs.add(cbs[id]);
            selectedIds.add(id);
          };
        });
      }, {
        var id = indices.at(sel);
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

  playMatches {
    arg ... pats;
    var selectedIds = Set.new;
    var selectedCbs = List.new;
    pats.do({
      arg pat;
      identifiers.do({
        arg identifier, id;
        selectedIds.includes(id).not && pat.matchRegexp(identifier) && {
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
