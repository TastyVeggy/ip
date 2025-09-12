# AI use within this project

Chatgpt was used in adding increment `A-BetterGui` as part of `A-AiAssisted` increment.

Accurate as of 12 September 2025.


## Prompt

The following prompt, preceded by a copy and paste of `src/main/java/yappy/ui/gui/DialogBox.java`, `src/main/resources/css/dialog-box.css` and `src/main/resources/view/DialogBox.fxml` code, was fed:

```
Tweak the GUI to match the asymmetric nature of the conversation: As the conversation is between the user and the app (not between two humans), it is asymmetric in nature. Accordingly, it makes sense not to display both sides of the conversion in the same visual format. Do keep the profile pictures
```

## Response

<pre>
Perfect 👍 thanks for showing me your current CSS + FXML + Java.
Right now both **user** and **Yappy** dialogs share almost the same bubble style (gradient background, border, rounded corners), and the only distinction is the `flip()` and command‐based colors.

Since you want the GUI to reflect the **asymmetric nature** of the conversation (user vs app) **while still keeping your profile picture**, the best approach is:

---

## 1. FXML tweak

Wrap the `Label` in an `HBox`/`VBox` so we can align differently for user vs Yappy, but keep the `ImageView` on both sides.

```xml
<fx:root alignment="TOP_RIGHT"
         maxHeight="Infinity" maxWidth="Infinity"
         prefWidth="400.0"
         stylesheets="@../css/dialog-box.css"
         type="javafx.scene.layout.HBox"
         xmlns="http://javafx.com/javafx/17"
         xmlns:fx="http://javafx.com/fxml/1">

  <children>
    <ImageView fx:id="displayPicture"
               fitHeight="50.0" fitWidth="50.0"
               pickOnBounds="true" preserveRatio="true" />

    <Label fx:id="dialog"
           minHeight="-Infinity"
           text="Label"
           wrapText="true" />
  </children>

  <padding>
    <Insets top="10" right="10" bottom="10" left="10"/>
  </padding>
</fx:root>
```

> Difference: avatar + text are side by side, but styling/alignment will now be handled in **Java + CSS** per speaker.

---

## 2. DialogBox.java tweak

Make **user messages** right‐aligned (avatar on the right, bubble distinct), and **Yappy messages** left‐aligned (avatar on the left, bot styling).

```java
public static DialogBox getUserDialog(String text, Image img) {
    var db = new DialogBox(text, img);
    db.setAlignment(Pos.TOP_RIGHT);
    db.dialog.getStyleClass().add("user-label");
    return db;
}

public static DialogBox getYappyDialog(String text, Image img, String commandName) {
    var db = new DialogBox(text, img);
    db.flip(); // put avatar left, text right
    db.changeDialogStyle(commandName);
    db.dialog.getStyleClass().add("bot-label");
    return db;
}
```

---

## 3. CSS update (dialog-box.css)

Make the **user bubble** colorful and distinctive, and the **bot bubble** calm, neutral, but still showing command‐specific highlights.

```css
/* USER bubble */
.user-label {
    -fx-background-color: linear-gradient(to bottom right, #00ffbf, #00ddff);
    -fx-text-fill: #003366;
    -fx-padding: 8 12;
    -fx-background-radius: 1em 1em 0 1em;
    -fx-border-radius: 1em 1em 0 1em;
}

/* BOT bubble (default neutral) */
.bot-label {
    -fx-background-color: #f5f5f5;
    -fx-text-fill: #333333;
    -fx-padding: 10 14;
    -fx-background-radius: 1em 1em 1em 0;
    -fx-border-radius: 1em 1em 1em 0;
}

/* Command-specific overrides for bot */
.add-label {
    -fx-background-color: yellow;
}

.marked-label {
    -fx-background-color: palegreen;
}

.delete-label {
    -fx-background-color: lightpink;
}

/* Avatar stays with subtle shadow */
#displayPicture {
    -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 8, 0.5, 3, 3);
    -fx-scale-x: 1;
    -fx-scale-y: 1;
}
```

---

✅ Result:

* **Your messages** → appear on the **right**, gradient colorful bubble, your profile picture still shown.
* **Yappy messages** → appear on the **left**, neutral gray (or command‐colored) bubble, with its avatar.
* This emphasizes the **asymmetry**: you = “user style” vs Yappy = “system style.”

---

Do you want me to also **round the corners differently** (e.g. user bubble sharp bottom-right, bot bubble sharp bottom-left) so it looks more like chat app “tails”?
</pre>


## Result

AI was generally worked able to get the job done, but it messed up the layout of the profile picture and the dialog box (due to it using flip for the chatbot instead of the user now). But that is a quick fix of the `DialogBox.fxml` to swap the two nodes and after that it works well. Overall, I think time is saved, though more caution was required as I had to make sure to test command, as I was not as sure if the AI did anything funny.
