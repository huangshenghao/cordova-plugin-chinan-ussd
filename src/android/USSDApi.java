//package com.romellfudi.ussdlibrary;

package com.ramymokako.plugin.ussd.android;

import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Romell Dominguez
 * @version 1.1.c 13/02/2018
 * @since 1.0.a
 */
public interface USSDApi {
  void cancel();

  void send(String text, USSDController.CallbackMessage callbackMessage);

  void callUSSDInvoke(String ussdPhoneNumber, HashMap<String, HashSet<String>> map,
      USSDController.CallbackInvoke callbackInvoke);

  void callUSSDInvoke(String ussdPhoneNumber, int simSlot, HashMap<String, HashSet<String>> map,
      USSDController.CallbackInvoke callbackInvoke);

  void callUSSDOverlayInvoke(String ussdPhoneNumber, HashMap<String, HashSet<String>> map,
      USSDController.CallbackInvoke callbackInvoke);

  void callUSSDOverlayInvoke(String ussdPhoneNumber, int simSlot, HashMap<String, HashSet<String>> map,
      USSDController.CallbackInvoke callbackInvoke);
}
