import { useEffect } from "react";
import "./Message.css";

export function Message({ text, type = "success", onClose, duration = 2000 }) {

  useEffect(() => {
    const timer = setTimeout(() => {
      onClose && onClose();
    }, duration);

    return () => clearTimeout(timer);
  }, [onClose, duration]);

  return (
    <div className={`message-container ${type}`}>
      {text}
    </div>
  );
}
