import { NativeModules } from "react-native";

const { ShareExtensionLib } = NativeModules;

export default {
  data: () => ShareExtensionLib.data(),
  close: () => ShareExtensionLib.close(),
  openURL: url => ShareExtensionLib.openURL(url)
};
