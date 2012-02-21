# Tapestry Conversation

Window and conversation scope for Tapestry web framework.

**This module is not working yet!**

## Usage

In your pages and components you can use the <code>@WindowState</code> annotation to mark objects as per-window.
This works similar to <code>@SessionState</code> but stores the data in the session per window. When the user
opens a new browser tab or window another object will be used (JavaScript needs to be activated to make this work).

    @WindowState
    private MyObject object;

The same applies to <code>@Persist("window")</code> which stores page state variables on a per-window basis in the session.

If you want to change the global persist strategy to per-window you have to change the configuration "tapestry.persistence-strategy": 

    public static void contributeApplicationDefaults(MappedConfiguration<String,String> configuration)
    {
      configuration.add(SymbolConstants.PERSISTENCE_STRATEGY, ConversationPersistenceConstants.WINDOW);
    }

## How it works

tbd ...