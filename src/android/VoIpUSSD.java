package com.ramymokako.plugin.ussd.android;

import android.Manifest.permission;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import android.content.pm.PackageManager;

//import io.sybox.easyshare.MainActivity; //(io.sybox.easyshare: this must be replaced by the name of your main package)

public class VoIpUSSD extends CordovaPlugin {

  private HashMap<String, HashSet<String>> map;
  private USSDApi ussdApi;
  private Context context;
  private String result;
  public final String ACTION_SEND_SMS = "show";
  public final String ACTION_HAS_PERMISSION = "has_permission";
  public final String ACTION_REQUEST_PERMISSION = "request_permission";
  public final String ACTION_SHOW_TEST = "show_test";
  public final String ACTION_SEND_TEXT = "send_text";
  public final String ACTION_CI_TEST = "ci_test";
  public final String ACTION_CI_MTN_PAY_OUT = "ci_mtn_pay_out";
  public final String ACTION_CI_ORANGE_PAY_OUT = "ci_orange_pay_out";
  public final String ACTION_CI_MOOV_PAY_OUT = "ci_moov_pay_out";
  private static final int SEND_SMS_REQ_CODE = 0;
  private static final int REQUEST_PERMISSION_REQ_CODE = 1;
  CallbackContext callbackContext;
  private JSONArray _args;

  @Override
  public boolean execute(String action, final JSONArray args, final CallbackContext callbackContext)
      throws JSONException {

    map = new HashMap<>();
    map.put("KEY_LOGIN", new HashSet<>(Arrays.asList("espere", "waiting", "loading", "esperando")));
    map.put("KEY_ERROR", new HashSet<>(Arrays.asList("problema", "problem", "error", "null")));
    this.context = cordova.getActivity();// .getApplicationContext();
    this.callbackContext = callbackContext;
    this._args = args;
    ussdApi = USSDController.getInstance(this.context);
    result = "";

    if (action.equals(ACTION_SEND_SMS)) {
      String ussdCode;
      try {
        JSONObject options = args.getJSONObject(0);
        ussdCode = options.getString("ussdCode");
      } catch (JSONException e) {
        callbackContext.error("Error encountered: " + e.getMessage());
        return false;
      }

      if (hasPermission()) {
        executeSimpleUssd(ussdCode, callbackContext);
        PluginResult pluginResult_NO_RESULT = new PluginResult(PluginResult.Status.NO_RESULT);
        pluginResult_NO_RESULT.setKeepCallback(true);
        return true;
      } else {
        requestPermission(SEND_SMS_REQ_CODE);
        return false;
      }
    } else if (action.equals(ACTION_CI_TEST)) {
      String ussdCode;
      try {
        JSONObject options = args.getJSONObject(0);
        ussdCode = options.getString("ussdCode");
      } catch (JSONException e) {
        callbackContext.error("Error encountered: " + e.getMessage());
        return false;
      }

      if (hasPermission()) {
        executeCiTest(ussdCode, callbackContext);
        PluginResult pluginResult_NO_RESULT = new PluginResult(PluginResult.Status.NO_RESULT);
        pluginResult_NO_RESULT.setKeepCallback(true);
        return true;
      } else {
        requestPermission(SEND_SMS_REQ_CODE);
        return false;
      }
    } else if (action.equals(ACTION_CI_MTN_PAY_OUT)) {
      String ussdCode;
      String pinCode;
      String account;
      String amount;

      try {
        JSONObject options = args.getJSONObject(0);
        ussdCode = options.getString("ussdCode");
        pinCode = options.getString("pinCode");
        account = options.getString("account");
        amount = options.getString("amount");
      } catch (JSONException e) {
        callbackContext.error("Error encountered: " + e.getMessage());
        return false;
      }

      if (hasPermission()) {
        executeCiMtnPayOut(ussdCode, pinCode, account, amount, callbackContext);
        PluginResult pluginResult_NO_RESULT = new PluginResult(PluginResult.Status.NO_RESULT);
        pluginResult_NO_RESULT.setKeepCallback(true);
        return true;
      } else {
        requestPermission(SEND_SMS_REQ_CODE);
        return false;
      }
    } else if (action.equals(ACTION_CI_ORANGE_PAY_OUT)) {
      String ussdCode;
      String pinCode;
      String account;
      String amount;

      try {
        JSONObject options = args.getJSONObject(0);
        ussdCode = options.getString("ussdCode");
        pinCode = options.getString("pinCode");
        account = options.getString("account");
        amount = options.getString("amount");
      } catch (JSONException e) {
        callbackContext.error("Error encountered: " + e.getMessage());
        return false;
      }

      if (hasPermission()) {
        executeCiOrangePayOut(ussdCode, pinCode, account, amount, callbackContext);
        PluginResult pluginResult_NO_RESULT = new PluginResult(PluginResult.Status.NO_RESULT);
        pluginResult_NO_RESULT.setKeepCallback(true);
        return true;
      } else {
        requestPermission(SEND_SMS_REQ_CODE);
        return false;
      }
    } else if (action.equals(ACTION_CI_MOOV_PAY_OUT)) {
      String ussdCode;
      String pinCode;
      String account;
      String amount;

      try {
        JSONObject options = args.getJSONObject(0);
        ussdCode = options.getString("ussdCode");
        pinCode = options.getString("pinCode");
        account = options.getString("account");
        amount = options.getString("amount");
      } catch (JSONException e) {
        callbackContext.error("Error encountered: " + e.getMessage());
        return false;
      }

      if (hasPermission()) {
        executeCiMoovPayOut(ussdCode, pinCode, account, amount, callbackContext);
        PluginResult pluginResult_NO_RESULT = new PluginResult(PluginResult.Status.NO_RESULT);
        pluginResult_NO_RESULT.setKeepCallback(true);
        return true;
      } else {
        requestPermission(SEND_SMS_REQ_CODE);
        return false;
      }
    } else if (action.equals(ACTION_SEND_TEXT)) {
      String text;
      try {
        JSONObject options = args.getJSONObject(0);
        text = options.getString("text");
      } catch (JSONException e) {
        callbackContext.error("Error encountered: " + e.getMessage());
        return false;
      }

      if (hasPermission()) {
        executeSendText(text, callbackContext);
        PluginResult pluginResult_NO_RESULT = new PluginResult(PluginResult.Status.NO_RESULT);
        pluginResult_NO_RESULT.setKeepCallback(true);
        return true;
      } else {
        requestPermission(SEND_SMS_REQ_CODE);
        return false;
      }
    } else if (action.equals(ACTION_HAS_PERMISSION)) {
      callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, hasPermission()));
      return false;
    } else if (action.equals(ACTION_REQUEST_PERMISSION)) {
      requestPermission(REQUEST_PERMISSION_REQ_CODE);
      return false;
    }
    return false;
  }

  private void executeSendText(String text, CallbackContext callbackContext) {
    ussdApi.send(text, new USSDController.CallbackMessage() {
      @Override
      public void responseMessage(String message, Boolean isRunning) {
        result += "\n-\n" + message;
        PluginResult result_1 = new PluginResult(PluginResult.Status.OK, message);
        result_1.setKeepCallback(true);
        callbackContext.sendPluginResult(result_1);
      }
    });
  }

  private void executeCiTest(String phone, CallbackContext callbackContext) {
    String phoneNumber = phone;
    ussdApi.callUSSDInvoke(phoneNumber, map, new USSDController.CallbackInvoke() {
      @Override
      public void responseInvoke(String message, Boolean isRunning) {
        result += "\n-\n" + message;
        PluginResult result_1 = new PluginResult(PluginResult.Status.OK, message);
        result_1.setKeepCallback(true);
        callbackContext.sendPluginResult(result_1);
        ussdApi.send("3", new USSDController.CallbackMessage() {
          @Override
          public void responseMessage(String message, Boolean isRunning) {
            result += "\n-\n" + message;
            PluginResult result_2 = new PluginResult(PluginResult.Status.OK, message);
            result_2.setKeepCallback(true);
            callbackContext.sendPluginResult(result_2);
            ussdApi.send("1", new USSDController.CallbackMessage() {
              @Override
              public void responseMessage(String message, Boolean isRunning) {
                result += "\n-\n" + message;
                PluginResult result_3 = new PluginResult(PluginResult.Status.OK, message);
                result_3.setKeepCallback(true);
                callbackContext.sendPluginResult(result_3);
                ussdApi.send("0595030599", new USSDController.CallbackMessage() {
                  @Override
                  public void responseMessage(String message, Boolean isRunning) {
                    result += "\n-\n" + message;
                    PluginResult result_4 = new PluginResult(PluginResult.Status.OK, message);
                    callbackContext.sendPluginResult(result_4);

                    ussdApi.send("50", new USSDController.CallbackMessage() {
                      @Override
                      public void responseMessage(String message, Boolean isRunning) {
                        result += "\n-\n" + message;
                        PluginResult result_5 = new PluginResult(PluginResult.Status.OK, message);
                        callbackContext.sendPluginResult(result_5);

                        ussdApi.send("1", new USSDController.CallbackMessage() {
                          @Override
                          public void responseMessage(String message, Boolean isRunning) {
                            result += "\n-\n" + message;
                            PluginResult result_6 = new PluginResult(PluginResult.Status.OK, message);
                            callbackContext.sendPluginResult(result_6);

                            ussdApi.send("test transfer", new USSDController.CallbackMessage() {
                              @Override
                              public void responseMessage(String message, Boolean isRunning) {
                                result += "\n-\n" + message;
                                PluginResult result_7 = new PluginResult(PluginResult.Status.OK, message);
                                callbackContext.sendPluginResult(result_7);

                                ussdApi.send("01091", new USSDController.CallbackMessage() {
                                  @Override
                                  public void responseMessage(String message, Boolean isRunning) {
                                    result += "\n-\n" + message;
                                    PluginResult result_8 = new PluginResult(PluginResult.Status.OK, message);
                                    callbackContext.sendPluginResult(result_8);

                                    ussdApi.cancel();

                                  }
                                });
                              }
                            });
                          }
                        });
                      }
                    });
                  }
                });
              }
            });
          }
        });
      }

      @Override
      public void over(String message) {
        result += "\n-\n" + message;
      }
    });
  }

  /**
   * ci mtn 放款
   * 
   * @param ussdCode        ussd码
   * @param pinCode         放款pin码
   * @param account         收款账户
   * @param amount          金额
   * @param callbackContext 回调内容
   */
  private void executeCiMtnPayOut(
      String ussdCode,
      String pinCode,
      String account,
      String amount,
      CallbackContext callbackContext) {
    String tem_ussdCode = ussdCode;
    String tem_pinCode = pinCode;
    String tem_account = account;
    String tem_amount = amount;

    try {
      ussdApi.callUSSDInvoke(tem_ussdCode, map, new USSDController.CallbackInvoke() {
        @Override
        public void responseInvoke(String message, Boolean isRunning) {
          result += "\n-\n" + message;
          PluginResult result_1 = new PluginResult(PluginResult.Status.OK, message + " -- " + (isRunning));
          result_1.setKeepCallback(true);
          callbackContext.sendPluginResult(result_1);
          ussdApi.send("3", new USSDController.CallbackMessage() {
            @Override
            public void responseMessage(String message, Boolean isRunning) {
              result += "\n-\n" + message;
              PluginResult result_2 = new PluginResult(PluginResult.Status.OK, message + " -- " + (isRunning));
              result_2.setKeepCallback(true);
              callbackContext.sendPluginResult(result_2);
              ussdApi.send("1", new USSDController.CallbackMessage() {
                @Override
                public void responseMessage(String message, Boolean isRunning) {
                  result += "\n-\n" + message;
                  PluginResult result_3 = new PluginResult(PluginResult.Status.OK, message + " -- " + (isRunning));
                  result_3.setKeepCallback(true);
                  callbackContext.sendPluginResult(result_3);
                  ussdApi.send(tem_account, new USSDController.CallbackMessage() {
                    @Override
                    public void responseMessage(String message, Boolean isRunning) {
                      result += "\n-\n" + message;
                      PluginResult result_4 = new PluginResult(PluginResult.Status.OK, message + " -- " + (isRunning));
                      result_4.setKeepCallback(true);
                      callbackContext.sendPluginResult(result_4);
                      ussdApi.send(tem_amount, new USSDController.CallbackMessage() {
                        @Override
                        public void responseMessage(String message, Boolean isRunning) {
                          result += "\n-\n" + message;
                          PluginResult result_5 = new PluginResult(PluginResult.Status.OK,
                              message + " -- " + (isRunning));
                          result_5.setKeepCallback(true);
                          callbackContext.sendPluginResult(result_5);
                          ussdApi.send("2", new USSDController.CallbackMessage() {
                            @Override
                            public void responseMessage(String message, Boolean isRunning) {
                              result += "\n-\n" + message;
                              PluginResult result_6 = new PluginResult(PluginResult.Status.OK,
                                  message + " -- " + (isRunning));
                              result_6.setKeepCallback(true);
                              callbackContext.sendPluginResult(result_6);
                              ussdApi.send(tem_pinCode, new USSDController.CallbackMessage() {
                                @Override
                                public void responseMessage(String message, Boolean isRunning) {
                                  result += "\n-\n" + message;
                                  PluginResult result_7 = new PluginResult(PluginResult.Status.OK,
                                      message + " -- " + (isRunning));
                                  result_7.setKeepCallback(true);
                                  callbackContext.sendPluginResult(result_7);
                                  // ussdApi.send(tem_pinCode, new USSDController.CallbackMessage() {
                                  // @Override
                                  // public void responseMessage(String message, Boolean isRunning) {
                                  // result += "\n-\n" + message;
                                  // PluginResult result_8 = new PluginResult(PluginResult.Status.OK,
                                  // message + " -- " + (isRunning));
                                  // result_8.setKeepCallback(true);
                                  // callbackContext.sendPluginResult(result_8);

                                  ussdApi.cancel();

                                  if (isRunning) {
                                    PluginResult result_9 = new PluginResult(PluginResult.Status.OK,
                                        "manual cancel" + " -- " + (!isRunning));
                                    result_9.setKeepCallback(true);
                                    callbackContext.sendPluginResult(result_9);
                                  }

                                  // }
                                  // });
                                }
                              });
                            }
                          });
                        }
                      });
                    }
                  });
                }
              });
            }
          });
        }

        @Override
        public void over(String message) {
          result += "\n-\n" + message;
        }
      });
    } catch (Exception ex) {
      PluginResult result_ex = new PluginResult(PluginResult.Status.ERROR, "--------- exception ex");
      result_ex.setKeepCallback(true);
      callbackContext.sendPluginResult(result_ex);
    }

    // PluginResult result_r = new PluginResult(PluginResult.Status.OK, "---------
    // done");
    // result_r.setKeepCallback(true);
    // callbackContext.sendPluginResult(result_r);

  }

  /**
   * ci orange 放款
   * 
   * @param ussdCode        ussd码
   * @param pinCode         放款pin码
   * @param account         收款账户
   * @param amount          金额
   * @param callbackContext 回调内容
   */
  private void executeCiOrangePayOut(
      String ussdCode,
      String pinCode,
      String account,
      String amount,
      CallbackContext callbackContext) {
    String tem_ussdCode = ussdCode;
    String tem_pinCode = pinCode;
    String tem_account = account;
    String tem_amount = amount;

    try {
      ussdApi.callUSSDInvoke(tem_ussdCode, map, new USSDController.CallbackInvoke() {
        @Override
        public void responseInvoke(String message, Boolean isRunning) {
          result += "\n-\n" + message;
          PluginResult result_1 = new PluginResult(PluginResult.Status.OK, message + " -- " + (isRunning));
          result_1.setKeepCallback(true);
          callbackContext.sendPluginResult(result_1);
          ussdApi.send("1", new USSDController.CallbackMessage() {
            @Override
            public void responseMessage(String message, Boolean isRunning) {
              result += "\n-\n" + message;
              PluginResult result_2 = new PluginResult(PluginResult.Status.OK, message + " -- " + (isRunning));
              result_2.setKeepCallback(true);
              callbackContext.sendPluginResult(result_2);
              ussdApi.send("1", new USSDController.CallbackMessage() {
                @Override
                public void responseMessage(String message, Boolean isRunning) {
                  result += "\n-\n" + message;
                  PluginResult result_3 = new PluginResult(PluginResult.Status.OK, message + " -- " + (isRunning));
                  result_3.setKeepCallback(true);
                  callbackContext.sendPluginResult(result_3);
                  ussdApi.send(tem_account, new USSDController.CallbackMessage() {
                    @Override
                    public void responseMessage(String message, Boolean isRunning) {
                      result += "\n-\n" + message;
                      PluginResult result_4 = new PluginResult(PluginResult.Status.OK, message + " -- " + (isRunning));
                      result_4.setKeepCallback(true);
                      callbackContext.sendPluginResult(result_4);
                      ussdApi.send(tem_amount, new USSDController.CallbackMessage() {
                        @Override
                        public void responseMessage(String message, Boolean isRunning) {
                          result += "\n-\n" + message;
                          PluginResult result_5 = new PluginResult(PluginResult.Status.OK,
                              message + " -- " + (isRunning));
                          result_5.setKeepCallback(true);
                          callbackContext.sendPluginResult(result_5);
                          ussdApi.send("1", new USSDController.CallbackMessage() {
                            @Override
                            public void responseMessage(String message, Boolean isRunning) {
                              result += "\n-\n" + message;
                              PluginResult result_6 = new PluginResult(PluginResult.Status.OK,
                                  message + " -- " + (isRunning));
                              result_6.setKeepCallback(true);
                              callbackContext.sendPluginResult(result_6);
                              ussdApi.send(tem_pinCode, new USSDController.CallbackMessage() {
                                @Override
                                public void responseMessage(String message, Boolean isRunning) {
                                  result += "\n-\n" + message;
                                  PluginResult result_7 = new PluginResult(PluginResult.Status.OK,
                                      message + " -- " + (isRunning));
                                  result_7.setKeepCallback(true);
                                  callbackContext.sendPluginResult(result_7);

                                  String dynamicCode = getOrangeDynamicCode(content);

                                  ussdApi.send(dynamicCode, new USSDController.CallbackMessage() {
                                    @Override
                                    public void responseMessage(String message, Boolean isRunning) {
                                      result += "\n-\n" + message;
                                      PluginResult result_8 = new PluginResult(PluginResult.Status.OK,
                                          message + " -- " + (isRunning));
                                      result_8.setKeepCallback(true);
                                      callbackContext.sendPluginResult(result_8);

                                      ussdApi.cancel();

                                      if (isRunning) {
                                        PluginResult result_9 = new PluginResult(PluginResult.Status.OK,
                                            "manual cancel" + " -- " + (!isRunning));
                                        result_9.setKeepCallback(true);
                                        callbackContext.sendPluginResult(result_9);
                                      }

                                    }
                                  });
                                }
                              });
                            }
                          });
                        }
                      });
                    }
                  });
                }
              });
            }
          });
        }

        @Override
        public void over(String message) {
          result += "\n-\n" + message;
        }
      });
    } catch (Exception ex) {
      PluginResult result_ex = new PluginResult(PluginResult.Status.ERROR, "--------- exception ex");
      result_ex.setKeepCallback(true);
      callbackContext.sendPluginResult(result_ex);
    }

    // PluginResult result_r = new PluginResult(PluginResult.Status.OK, "---------
    // done");
    // result_r.setKeepCallback(true);
    // callbackContext.sendPluginResult(result_r);
  }

  public static String getOrangeDynamicCode(String content) {
    String code = "";

    try {
      if (content.contains("composez ") && content.contains(" puis ok.")) {
        code = content.split("composez ")[1].split(" puis ok.")[0];
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      System.out.println(ex.getMessage());
    }

    return code;
  }

  /**
   * ci moov 放款
   * 
   * @param ussdCode        ussd码
   * @param pinCode         放款pin码
   * @param account         收款账户
   * @param amount          金额
   * @param callbackContext 回调内容
   */
  private void executeCiMoovPayOut(
      String ussdCode,
      String pinCode,
      String account,
      String amount,
      CallbackContext callbackContext) {
    String tem_ussdCode = ussdCode;
    String tem_pinCode = pinCode;
    String tem_account = account;
    String tem_amount = amount;

    try {
      ussdApi.callUSSDInvoke(tem_ussdCode, map, new USSDController.CallbackInvoke() {
        @Override
        public void responseInvoke(String message, Boolean isRunning) {
          result += "\n-\n" + message;
          PluginResult result_1 = new PluginResult(PluginResult.Status.OK, message + " -- " + (isRunning));
          result_1.setKeepCallback(true);
          callbackContext.sendPluginResult(result_1);
          ussdApi.send("1", new USSDController.CallbackMessage() {
            @Override
            public void responseMessage(String message, Boolean isRunning) {
              result += "\n-\n" + message;
              PluginResult result_2 = new PluginResult(PluginResult.Status.OK, message + " -- " + (isRunning));
              result_2.setKeepCallback(true);
              callbackContext.sendPluginResult(result_2);
              ussdApi.send("1", new USSDController.CallbackMessage() {
                @Override
                public void responseMessage(String message, Boolean isRunning) {
                  result += "\n-\n" + message;
                  PluginResult result_3 = new PluginResult(PluginResult.Status.OK, message + " -- " + (isRunning));
                  result_3.setKeepCallback(true);
                  callbackContext.sendPluginResult(result_3);
                  ussdApi.send(tem_account, new USSDController.CallbackMessage() {
                    @Override
                    public void responseMessage(String message, Boolean isRunning) {
                      result += "\n-\n" + message;
                      PluginResult result_4 = new PluginResult(PluginResult.Status.OK, message + " -- " + (isRunning));
                      result_4.setKeepCallback(true);
                      callbackContext.sendPluginResult(result_4);
                      ussdApi.send(tem_amount, new USSDController.CallbackMessage() {
                        @Override
                        public void responseMessage(String message, Boolean isRunning) {
                          result += "\n-\n" + message;
                          PluginResult result_5 = new PluginResult(PluginResult.Status.OK,
                              message + " -- " + (isRunning));
                          result_5.setKeepCallback(true);
                          callbackContext.sendPluginResult(result_5);
                          ussdApi.send(tem_pinCode, new USSDController.CallbackMessage() {
                            @Override
                            public void responseMessage(String message, Boolean isRunning) {
                              result += "\n-\n" + message;
                              PluginResult result_6 = new PluginResult(PluginResult.Status.OK,
                                  message + " -- " + (isRunning));
                              result_6.setKeepCallback(true);
                              callbackContext.sendPluginResult(result_6);
                              ussdApi.send("1", new USSDController.CallbackMessage() {
                                @Override
                                public void responseMessage(String message, Boolean isRunning) {
                                  result += "\n-\n" + message;
                                  PluginResult result_7 = new PluginResult(PluginResult.Status.OK,
                                      message + " -- " + (isRunning));
                                  result_7.setKeepCallback(true);
                                  callbackContext.sendPluginResult(result_7);

                                  ussdApi.cancel();

                                  if (isRunning) {
                                    PluginResult result_9 = new PluginResult(PluginResult.Status.OK,
                                        "manual cancel" + " -- " + (!isRunning));
                                    result_9.setKeepCallback(true);
                                    callbackContext.sendPluginResult(result_9);
                                  }

                                }
                              });
                            }
                          });
                        }
                      });
                    }
                  });
                }
              });
            }
          });
        }

        @Override
        public void over(String message) {
          result += "\n-\n" + message;
        }
      });
    } catch (Exception ex) {
      PluginResult result_ex = new PluginResult(PluginResult.Status.ERROR, "--------- exception ex");
      result_ex.setKeepCallback(true);
      callbackContext.sendPluginResult(result_ex);
    }

    // PluginResult result_r = new PluginResult(PluginResult.Status.OK, "---------
    // done");
    // result_r.setKeepCallback(true);
    // callbackContext.sendPluginResult(result_r);

  }

  private void executeTestUssd(String phone, CallbackContext callbackContext) {
    String phoneNumber = phone;
    ussdApi.callUSSDInvoke(phoneNumber, map, new USSDController.CallbackInvoke() {
      @Override
      public void responseInvoke(String message, Boolean isRunning) {
        result += "\n-\n" + message;
        PluginResult result_1 = new PluginResult(PluginResult.Status.OK, message);
        result_1.setKeepCallback(true);
        callbackContext.sendPluginResult(result_1);
        ussdApi.send("1", new USSDController.CallbackMessage() {
          @Override
          public void responseMessage(String message, Boolean isRunning) {
            result += "\n-\n" + message;
            PluginResult result_2 = new PluginResult(PluginResult.Status.OK, message);
            result_2.setKeepCallback(true);
            callbackContext.sendPluginResult(result_2);
            ussdApi.send("1", new USSDController.CallbackMessage() {
              @Override
              public void responseMessage(String message, Boolean isRunning) {
                result += "\n-\n" + message;
                PluginResult result_3 = new PluginResult(PluginResult.Status.OK, message);
                result_3.setKeepCallback(true);
                callbackContext.sendPluginResult(result_3);
                ussdApi.send("0531795014", new USSDController.CallbackMessage() {
                  @Override
                  public void responseMessage(String message, Boolean isRunning) {
                    result += "\n-\n" + message;
                    PluginResult result_4 = new PluginResult(PluginResult.Status.OK, message);
                    callbackContext.sendPluginResult(result_4);

                    ussdApi.send("0531795014", new USSDController.CallbackMessage() {
                      @Override
                      public void responseMessage(String message, Boolean isRunning) {
                        result += "\n-\n" + message;
                        PluginResult result_5 = new PluginResult(PluginResult.Status.OK, message);
                        callbackContext.sendPluginResult(result_5);

                        ussdApi.send("1", new USSDController.CallbackMessage() {
                          @Override
                          public void responseMessage(String message, Boolean isRunning) {
                            result += "\n-\n" + message;
                            PluginResult result_6 = new PluginResult(PluginResult.Status.OK, message);
                            callbackContext.sendPluginResult(result_6);

                            ussdApi.send("test transfer", new USSDController.CallbackMessage() {
                              @Override
                              public void responseMessage(String message, Boolean isRunning) {
                                result += "\n-\n" + message;
                                PluginResult result_7 = new PluginResult(PluginResult.Status.OK, message);
                                callbackContext.sendPluginResult(result_7);

                                ussdApi.send("#", new USSDController.CallbackMessage() {
                                  @Override
                                  public void responseMessage(String message, Boolean isRunning) {
                                    result += "\n-\n" + message;
                                    PluginResult result_8 = new PluginResult(PluginResult.Status.OK, message);
                                    callbackContext.sendPluginResult(result_8);

                                    ussdApi.send("2580", new USSDController.CallbackMessage() {
                                      @Override
                                      public void responseMessage(String message, Boolean isRunning) {
                                        result += "\n-\n" + message;
                                        PluginResult result_9 = new PluginResult(PluginResult.Status.OK, message);
                                        callbackContext.sendPluginResult(result_9);

                                        ussdApi.send("#", new USSDController.CallbackMessage() {
                                          @Override
                                          public void responseMessage(String message, Boolean isRunning) {
                                            result += "\n-\n" + message;
                                            PluginResult result_10 = new PluginResult(PluginResult.Status.OK, message);
                                            callbackContext.sendPluginResult(result_10);
                                            ussdApi.cancel();
                                          }
                                        });

                                      }
                                    });
                                  }
                                });
                              }
                            });
                          }
                        });
                      }
                    });
                  }
                });
              }
            });
          }
        });
      }

      @Override
      public void over(String message) {
        result += "\n-\n" + message;
      }
    });
  }

  private void executeSimpleUssd(String phone, CallbackContext callbackContext) {
    String phoneNumber = phone;
    ussdApi.callUSSDInvoke(phoneNumber, map, new USSDController.CallbackInvoke() {
      @Override
      public void responseInvoke(String message, Boolean isRunning) {
        result += "\n-\n" + message;
        PluginResult result_1 = new PluginResult(PluginResult.Status.OK, result);
        result_1.setKeepCallback(true);
        callbackContext.sendPluginResult(result_1);
        // first option list - select option 1
        ussdApi.send("1", new USSDController.CallbackMessage() {
          @Override
          public void responseMessage(String message, Boolean isRunning) {
            result += "\n-\n" + message;
            PluginResult result_2 = new PluginResult(PluginResult.Status.OK, result);
            result_2.setKeepCallback(true);
            callbackContext.sendPluginResult(result_2);
            // second option list - select option 1
            ussdApi.send("1", new USSDController.CallbackMessage() {
              @Override
              public void responseMessage(String message, Boolean isRunning) {
                result += "\n-\n" + message;
                PluginResult result_3 = new PluginResult(PluginResult.Status.OK, result);
                result_3.setKeepCallback(true);
                callbackContext.sendPluginResult(result_3);
              }
            });
          }
        });
      }

      @Override
      public void over(String message) {
        result += "\n-\n" + message;
      }
    });
  }

  private boolean hasPermission() {
    boolean gyg1 = cordova.hasPermission(android.Manifest.permission.CALL_PHONE);
    boolean gyg2 = cordova.hasPermission(android.Manifest.permission.READ_PHONE_STATE);
    return (gyg1 == true && gyg2 == true);
  }

  private void requestPermission(int requestCode) {
    cordova.requestPermissions(this, requestCode,
        new String[] { android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.CALL_PHONE });
  }

  public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults)
      throws JSONException {
    for (int r : grantResults) {
      if (r == PackageManager.PERMISSION_DENIED) {
        this.callbackContext
            .sendPluginResult(new PluginResult(PluginResult.Status.ERROR, "User has denied permission"));
        return;
      }
    }
    if (requestCode == SEND_SMS_REQ_CODE) {

      String ussdCode;
      try {
        JSONObject options = this._args.getJSONObject(0);
        ussdCode = options.getString("ussdCode");
      } catch (JSONException e) {
        this.callbackContext.error("Error encountered: " + e.getMessage());
        return;
      }

      executeSimpleUssd(ussdCode, this.callbackContext);
      PluginResult pluginResult_NO_RESULT = new PluginResult(PluginResult.Status.NO_RESULT);
      pluginResult_NO_RESULT.setKeepCallback(true);
      return;
    }
    this.callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, true));
  }
}
