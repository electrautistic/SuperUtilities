PlusUtilities {

  *new {
    |input|
    ^this.asMidiRange(input);
  }

  *asMidiRange {
    |input|

    // return...
    // rercusive funkyness
    if(input.isKindOf(Array) or: (input.isKindOf(List))) {
      ^input.collect({ |i| this.asMidiRange(i) });
    };

    // or the value
    ^(127 * input).floor;
  }

  // requires ChordSymbol: https://github.com/triss/ChordSymbol
  *asMidiOctave {
    |array|
    ^array.collect {
      |note|
      var n, noct, oct;
      noct = if(note.isInteger, { note }, { note.asNote});
      n = if(noct.size == 0, { noct }, { noct[0] });
      oct = if(noct.size == 0, { 5 }, { noct[1] });
      12*oct+n;
    };
  }

  /*
  Based on Steven Yi's Hex Beats.
  http://kunstmusik.com/2017/10/20/hex-beats/
  */
  *hexBeat {
    |beat|
    // reject anything ouside hex valid numbers
    ^beat.asList.reject{
      |chr|
      "0123456789abcdef".asList.indexOfEqual(chr).isNil;
    }
    .collect{
      |hex|
      // convert each character/number to a 4bits representation
      hex.asString.asList.collect{
        |h|
        h.digit.asBinaryDigits(4)
      };
    }.flat;
  }

}


+ SequenceableCollection {

  midiRange {
    ^PlusUtilities.asMidiRange(this);
  }

  midiOctave {
    ^PlusUtilities.asMidiOctave(this);
  }

  hexBeat {
    ^PlusUtilities.hexBeat(this.asString).flat;
  }

}


+ String {

  hexBeat {
    ^PlusUtilities.hexBeat(this);
  }

  // requires Bjorklund Quark.
  asBjorklund {
    |k,n|
    ^Bjorklund(k,n).collect { |p| if (p.asBoolean) { this.asSymbol } { \r } };
  }

}
