class IndexMark {
  final int index;
  int count = 0;

  IndexMark(this.index);

  @override
  bool operator ==(Object other) => other is IndexMark && other.index == index;

  @override
  int get hashCode => index.hashCode;

  static IndexMark of(int index) {
    return IndexMark(index);
  }

  void incrementCount() {
    this.count++;
  }

}
