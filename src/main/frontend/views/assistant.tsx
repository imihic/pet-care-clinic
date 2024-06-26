import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { useSignal } from '@vaadin/hilla-react-signals';
import { Button } from '@vaadin/react-components/Button.js';
import { Notification } from '@vaadin/react-components/Notification.js';
import { TextField } from '@vaadin/react-components/TextField.js';

export const config: ViewConfig = {
  menu: { order: 5, icon: 'line-awesome/svg/telegram-plane.svg' },
  title: 'Assistant',
  loginRequired: true,
};

export default function AssistantView() {
  const name = useSignal('');

  return (
    <>
      <section className="flex p-m gap-m items-end">
        <TextField
          label="Your name"
          onValueChanged={(e) => {
            name.value = e.detail.value;
          }}
        />
        <Button
          onClick={async () => {
            Notification.show("Hello, " + name.value + "!");
          }}
        >
          Say hello
        </Button>
      </section>
    </>
  );
}
