import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class MoodCardWidget extends StatelessWidget {
  final Widget child;

  const MoodCardWidget({Key? key, required this.child}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Card(
      child: Padding(
        padding: EdgeInsets.all(6),
        child: this.child,
      ),
    );
  }
}
