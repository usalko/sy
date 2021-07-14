import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class ErrorMessageWidget extends StatelessWidget {

  String message;

  ErrorMessageWidget(this.message);

  @override
  Widget build(BuildContext context) {
    return new Padding(
        padding: const EdgeInsets.all(6),
        child: new Column(
          children: [
            Text(this.message,
                style: TextStyle(
                    fontSize: 10, color: Theme.of(context).disabledColor))
          ],
        ));
  }
}
