import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class MoodActionsWidget extends StatelessWidget {
  final VoidCallback? onShare;
  final VoidCallback? onClose;
  final VoidCallback? onKeepForNow;

  const MoodActionsWidget(
      {Key? key, this.onShare, this.onClose, this.onKeepForNow})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: 130,
      child: Padding(
        padding: const EdgeInsets.all(0),
        child: Card(
            color: Theme.of(context).canvasColor,
            elevation: 0,
            child: Column(
              children: [
                Padding(
                  padding: const EdgeInsets.only(top: 16),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      IconButton(
                        onPressed: onShare,
                        icon: const Icon(Icons.share),
                      ),
                      IconButton(
                        onPressed: onClose,
                        icon: const Icon(Icons.close),
                      ),
                    ],
                  ),
                ),
                Spacer(),
                Align(
                  alignment: Alignment.topRight,
                  child: Padding(
                    padding: const EdgeInsets.only(right: 22),
                    child: IconButton(
                      onPressed: onKeepForNow,
                      icon: const Icon(Icons.save),
                    ),
                  ),
                ),
                SizedBox(
                  height: 40,
                ),
              ],
            )),
      ),
    );
  }
}
