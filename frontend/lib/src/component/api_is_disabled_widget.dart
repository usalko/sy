import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class ApiIsDisabledWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Padding(
        padding: const EdgeInsets.all(6),
        child: new Column(
          children: [
            Text(
              'API is disabled',
              style: TextStyle(
                  fontWeight: FontWeight.w700,
                  fontSize: 22,
                  color: Theme.of(context).disabledColor),
            ),
            Text('For demo request mail to: ivict@rambler.ru',
                style: TextStyle(
                    fontSize: 10, color: Theme.of(context).disabledColor))
          ],
        ));
  }
}
